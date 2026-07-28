package com.sidav.moa.game.colony

import com.sidav.moa.game.empire.Empire
import com.sidav.moa.game.space.PlanetGrowth
import com.sidav.moa.game.space.PlanetSpecial
import com.sidav.moa.game.space.Star
import com.sidav.moa.util.floor
import kotlin.math.roundToInt

class Colony(var empire: Empire?, val star: Star, population: Float, factories: Float) {
    val budget = Budget(this)
    var population = population
        private set
    var factories = factories
        private set
    // Factories per pop
    var currRoboticControls: Float = empire?.bestRoboticControls() ?: 0f
        private set
    var bases = 0f
        private set
    var reserve = 0f
        private set

    init {
        governBudgets()
    }

    fun asString(): String {
        return "POP ${population.toInt()}/${star.pCurrentSizeAfterWaste().toInt()}\n" +
            "WAST ${star.pWaste}+${generatedWaste()}\n"+
            "FAC ${factories.toInt()}/${maxFactoriesForPopulation(population).toInt()}\n" +
            "PROD ${grossBcIncome().toInt()}"
    }

    fun grossBcIncome(): Float {
        if (empire == null) return 0f
        val empire = empire!!
        var income = 0f
        // Base production from population
        income += population.floor() * (empire.race.baseBcFromPopulation())
        income += activeFactories() * empire.productionPerFactory()
        if (star.planetSpecial == PlanetSpecial.RICH) {
            income *= 2
        }
        if (star.planetSpecial == PlanetSpecial.ULTRARICH) {
            income *= 3
        }
        return income
    }

    fun maxFactoriesForPopulation(pop: Float): Float {
        if (empire == null) return 0f
        return pop.floor() * currRoboticControls
    }

    fun activeFactories() =
        minOf(factories.floor(), population.floor() * currRoboticControls)

    fun generatedWaste(): Int =
        (activeFactories() * (empire?.wastePerOperatingFactory() ?: 0f)).roundToInt()

    fun totalWaste(): Int = generatedWaste() + star.pWaste

    fun netBCIncome(): Float {
        return grossBcIncome() // TODO: taxes and security spending
    }

    fun maxPopReached(): Boolean {
        return star.pCurrentSizeNoWaste() == population
    }

    fun nextNaturalPopulationGrowth(): Float {
        if (population == star.pCurrentSizeAfterWaste()) {
            return 0f
        }
        // TODO: races ignoring the waste
        var growthFraction = (1 - ((population + star.pWaste) / star.pCurrentSizeNoWaste())) / 10f;
        growthFraction *= when (star.planetGrowth) {
            PlanetGrowth.NORMAL -> 1f
            PlanetGrowth.HOSTILE -> if (growthFraction >= 0) 0.5f else 2f
            PlanetGrowth.FERTILE -> if (growthFraction >= 0) 1.5f else 0.75f
            PlanetGrowth.GAIA -> if (growthFraction >= 0) 2f else 0.5f
        }
        var growth = growthFraction * (population + 5)
        if (growth < 0.1f && growthFraction > 0) growth = 0.1f
        return growth
    }

    fun nextTurn() {
        governBudgets()
        val economyStep = PendingEconomyStep(
            population,
            netBCIncome(),
            reserve,
            star.pWaste,
            generatedWaste()
        )
        population += nextNaturalPopulationGrowth()
        budget.get(SpendingArea.SHP).calculateEconomyStep(economyStep)
        budget.get(SpendingArea.DEF).calculateEconomyStep(economyStep)
        budget.get(SpendingArea.IND).calculateEconomyStep(economyStep)
        budget.get(SpendingArea.ECO).calculateEconomyStep(economyStep)
        budget.get(SpendingArea.SCI).calculateEconomyStep(economyStep)
        applyEconomyStep(economyStep)
        governBudgets()
    }

    // This is NOT an "AI governor", this func just prevents useless slider actions
    private fun governBudgets() {
        // Ensure that we do clean current waste (if it's not locked)
        val eco = budget.eco()
        if (!eco.locked && eco.ticksAllocated < eco.ticksRequiredForWasteElimination()) {
            budget.setAllocSliderTicks(SpendingArea.ECO, minOf(maxSpendingTicks, eco.ticksRequiredForWasteElimination(generatedWaste())))
        }
    }


    private fun applyEconomyStep(es: PendingEconomyStep) {
        // IND
        factories += es.newFactories
        if (es.roboticControlsImproveFinished)
            empire?.bestRoboticControls()?.let { currRoboticControls = it }
        // ECO
        println("waste change is ${es.wasteChange}")
        star.pWaste = minOf(star.pWaste + es.wasteChange, star.maxWaste())
        star.pTerraformLevel += es.terraformingChange
        population += es.boughtPop
        // TECH
        empire?.techTree?.progressResearches(es.newRp)
        // misc
        reserve = es.unspentBc
    }
}
