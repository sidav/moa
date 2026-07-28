package com.sidav.moa.game.tech

import com.sidav.moa.game.empire.Empire
import com.sidav.moa.game.ship.ShipSize
import com.sidav.moa.game.tech.items.BaseTechItem
import com.sidav.moa.game.tech.items.ship_parts.BaseShipPart
import com.sidav.moa.game.tech.items.ship_parts_tech.BaseShipPartGivingTech
import com.sidav.moa.util.square
import kotlin.math.pow
import kotlin.random.Random

// This belongs to Empire and this is NOT the list of all techs (but it contains researchable tech)
class TechTree(val empire: Empire) {
    private val potentialTechs = TechField.entries.associateWith { mutableListOf<BaseTechItem>() }
    val ownedTech = TechField.entries.associateWith { mutableListOf<BaseTechItem>() }
    val researchInProgress = TechField.entries.associateWith { OngoingResearch() }
    var justResearchedTech = mutableListOf<BaseTechItem>()

    companion object {
        const val HULL_SIZE_GROWTH_BASE = 1.02
        const val BASE_RESEARCH_COST = 30
    }

    override fun toString(): String {
        var str = "Owned techs:"
        for (techField in TechField.entries) {
            str += "\n  ${techField.name}"
            for (t in ownedTech.getValue(techField)) {
                str += "\n    T${t.techLevel} ${t.name} (${techResearchCost(t)}RP)"
            }
        }
        str += "\nPotential techs:"
        for (techField in TechField.entries) {
            str += "\n  ${techField.name}"
            for (t in potentialTechs.getValue(techField)) {
                str += "\n    T${t.techLevel} ${t.name} (${techResearchCost(t)}RP)"
            }
        }
        str += "\nResearchable techs"
        for (techField in TechField.entries) {
            str += "\n  ${techField.name}"
            for (t in researchableTechs(techField)) {
                str += "\n    T${t.techLevel} ${t.name} (${techResearchCost(t)}RP)"
            }
        }
        return str
    }

    fun addStartingTechs() {
        for (techField in TechField.entries) {
            val techsInField = AllTechs.getValue(techField)
            for (t in techsInField) {
                if (t.isStartingTech) ownedTech.getValue(techField).add(t)
            }
        }
    }

    // This forms a random list of techs
    fun generateTechTree(prcToSelect: Int) {
        for (techField in TechField.entries) {
            val techsInField = AllTechs.getValue(techField)
            // TODO: REMOVE THIS DEBUG CODE:
            val techWithBadField = techsInField.filter { it.tField != techField }
            if (techWithBadField.isNotEmpty()) {
                for (t in techWithBadField)
                    println("Tech ${t.name} has field set to ${t.tField} but is placed to ${techField.name}")
                error("Tech list is corrupted, see logs")
            }
            // TODO: DEBUG CODE ENDED
            val listToFill = potentialTechs.getValue(techField)
            if (listToFill.isNotEmpty()) error("Researchable techs not empty - tree already generated?")

            for (t in techsInField) {
                if (t.isStartingTech) continue
                if (Random.nextInt(100) < prcToSelect) listToFill.add(t)
            }
            // Fail-safe: if any quintile is not filled, add a random tech from the quintile
            println("${techField.name}: filling quintiles")
            for (quintile in 1..10) {
                if (listToFill.any { it.quintile == quintile }) continue // TODO: should it EXCLUDE starting tech?
                val techsInQuintile = techsInField.filter { it.quintile == quintile }
                val techToAdd = techsInQuintile.randomOrNull()
                if (techToAdd != null) {
                    println("Quintile $quintile empty; adding ${techToAdd.name}")
                    listToFill.add(techToAdd)
                } else {
                    println("Quintile $quintile empty, but unable to add tech")
                }
            }
            listToFill.sortBy { it.techLevel }
        }
    }

    fun techLevelInField(tField: TechField): Int {
//         Yes, it's not the same as "tech level of highest researched tech" lol
//         From MOO1 strategy guide:
//         To determine a given sector's Tech level, add the following:
//         • 80 percent of the base Tech level of your most advanced discovery in that sector
//                (rounded down)
//         • +1
//         • The total number of items that you've discovered in that sector
//                 ( excluding the ones with which you started the game)
        var maxTl = 0
        var discoveryCount = 0

        for (tech in ownedTech.getValue(tField)) {
            if (tech.isStartingTech) continue // Exclude starting tech
            discoveryCount++
            if (tech.techLevel > maxTl) maxTl = tech.techLevel
        }
        if (discoveryCount > 0) discoveryCount--  // tech with maxTl does not count in discoveryCount here

        return (maxTl * 8) / 10 + 1 + discoveryCount
    }

    fun actualShipSpace(shipSize: ShipSize): Int {
        val constrTechLevel = techLevelInField(TechField.CONSTRUCTION)
        val factor = HULL_SIZE_GROWTH_BASE.pow(constrTechLevel)
        return (shipSize.baseSpace.toDouble() * factor).toInt()
    }

    fun progressResearches(rp: Float) {
        val fieldsWithResearch = researchInProgress.count { it.value.currentResearchedTech != null }
        if (fieldsWithResearch == 0) return
        for (area in TechField.entries) {
            if (researchInProgress.getValue(area).currentResearchedTech == null) continue
            val rpPerField = rp / fieldsWithResearch // TODO: per-field budgets
            println("Researching ${area.name}: +$rpPerField")
            researchInProgress.getValue(area).rpSpent += rpPerField
        }
        println("Research progress $researchInProgress")
    }

    fun finishCompletedResearch() {
        justResearchedTech.clear()
        for (resInProgress in researchInProgress.values) {
            val currRes = resInProgress.currentResearchedTech
            if (currRes != null && resInProgress.rpSpent >= techResearchCost(currRes)) {
                justResearchedTech.add(currRes)
                ownedTech.getValue(currRes.tField).add(currRes)
                resInProgress.rpSpent = 0f
                resInProgress.currentResearchedTech = null
            }
        }
    }

    fun setCurrentResearch(tech: BaseTechItem) {
        researchInProgress.getValue(tech.tField).currentResearchedTech = tech
    }

    fun techResearchCost(tech: BaseTechItem): Int {
        return BASE_RESEARCH_COST * tech.techLevel.square() // TODO: racial modifiers per-field
    }

    fun fieldsWithNoOngoingResearch(): List<TechField> =
        researchInProgress.filterValues { it.currentResearchedTech == null }.keys.toList()

    // What is researchable right now?
    fun researchableTechs(tField: TechField): List<BaseTechItem> {
        val researchable = potentialTechs.getValue(tField)
        val owned = ownedTech.getValue(tField)
        val ownedSet = owned.toHashSet()
        val maxAvailableQuintile = 1 + (owned.maxOfOrNull { it.quintile } ?: 0)
        return researchable.filter { it.quintile <= maxAvailableQuintile && it !in ownedSet }
    }

    // TODO: optimize (flatten is a redundant allocation)
    inline fun <reified T : BaseTechItem> bestTech(): T? =
        ownedTechOfType<T>().maxByOrNull { it.techLevel }

    inline fun <reified T : BaseTechItem> ownedTechOfType(): List<T> =
        ownedTech.values.flatten().filterIsInstance<T>()

    // TODO: optimize (flatten, filter, flatmap and another filter are too much)
    inline fun <reified T : BaseShipPart> ownedPartsOfType(): List<T> =
        ownedTech.values.flatten()
            .filterIsInstance<BaseShipPartGivingTech<*>>()
            .flatMap { it.givesItems }
            .filterIsInstance<T>()
}
