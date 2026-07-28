package com.sidav.moa.game.tech

import com.sidav.moa.game.empire.Empire
import com.sidav.moa.game.tech.items.BaseTechItem
import com.sidav.moa.util.square
import kotlin.random.Random

// This belongs to Empire and this is NOT the list of all techs (but it contains researchable tech)
class TechTree(val empire: Empire) {
    private val potentialTechs = TechField.entries.associateWith { mutableListOf<BaseTechItem>() }
    val ownedTech = TechField.entries.associateWith { mutableListOf<BaseTechItem>() }
    val researchInProgress = TechField.entries.associateWith { OngoingResearch() }
    var justResearchedTech = mutableListOf<BaseTechItem>()

    override fun toString(): String {
        var str = "Potential techs:"
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
            val listToFill = potentialTechs.getValue(techField)
            if (listToFill.isNotEmpty()) error("Researchable techs not empty - tree already generated?")

            for (t in techsInField) {
                if (Random.nextInt(100) < prcToSelect) listToFill.add(t)
            }
            // Fail-safe: if any quintile is not filled, add a random tech from the quintile
            for (quintile in 1..10) {
                if (listToFill.any { it.quintile == quintile }) continue
                val techsInQuintile = techsInField.filter { it.quintile == quintile }
                val techToAdd = techsInQuintile.randomOrNull()
                if (techToAdd != null) {
                    println("Quintile $quintile empty; adding ${techToAdd.name}")
                    listToFill.add(techToAdd)
                }
            }
            listToFill.sortBy { it.techLevel }
        }
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
                ownedTech.getValue(currRes.field).add(currRes)
                resInProgress.rpSpent = 0f
                resInProgress.currentResearchedTech = null
            }
        }
    }

    fun setCurrentResearch(tech: BaseTechItem) {
        researchInProgress.getValue(tech.field).currentResearchedTech = tech
    }

    fun techResearchCost(tech: BaseTechItem): Int {
        val baseCost = 30
        return baseCost * tech.techLevel.square() // TODO: racial modifiers per-field
    }

    fun fieldsWithNoOngoingResearch(): List<TechField> =
        researchInProgress.filterValues { it.currentResearchedTech == null }.keys.toList()

    // What is researchable right now?
    fun researchableTechs(field: TechField): List<BaseTechItem> {
        val researchable = potentialTechs.getValue(field)
        val owned = ownedTech.getValue(field)
        val ownedSet = owned.toHashSet()
        val maxAvailableQuintile = 1 + (owned.maxOfOrNull { it.quintile } ?: 0)
        return researchable.filter { it.quintile <= maxAvailableQuintile && it !in ownedSet }
    }

    // TODO: optimize (flatten is a redundant allocation)
    inline fun <reified T : BaseTechItem> bestTech(): T? =
        ownedTech.values.flatten().filterIsInstance<T>().maxByOrNull { it.techLevel }

}
