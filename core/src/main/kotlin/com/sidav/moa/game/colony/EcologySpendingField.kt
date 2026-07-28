package com.sidav.moa.game.colony

import com.sidav.moa.util.ceilInt
import com.sidav.moa.util.floor
import com.sidav.moa.util.toFixed1
import kotlin.math.min
import kotlin.math.roundToInt

class EcologySpendingField(colony: Colony) : ColonySpendingField(colony) {
    private var ongoingUpgradeBc = 0f
    override fun text(): String {
        val virtualEconomyStep = PendingEconomyStep(
            colony.population, colony.netBCIncome(), 0f, colony.star.pWaste, colony.generatedWaste()
        )
        calculateEconomyStep(virtualEconomyStep)
        return when {
            virtualEconomyStep.terraformingChange > 0 -> "T-FORM"
            virtualEconomyStep.boughtPop >= 0.5f -> "+${virtualEconomyStep.boughtPop.toFixed1()}P"
            virtualEconomyStep.wasteChange > 0 -> "WASTE"
            virtualEconomyStep.wasteChange < 0 -> "RSTR"
            colony.maxPopReached() && (ticksAllocated > ticksRequiredForWasteElimination(colony.totalWaste())) -> "MAX"
            else -> "CLEAN"
        }
    }

    fun bcRequiredForWasteElimination(wasteAmount: Int): Float {
        if (empire == null) return 0f
        return wasteAmount * empire.wasteCleanupCost()
    }

    fun ticksRequiredForWasteElimination(wasteAmount: Int = colony.generatedWaste() + colony.star.pWaste): Int {
        val bcReq = bcRequiredForWasteElimination(wasteAmount)
        if (bcReq == 0f) return 0
        return minOf(
            maxSpendingTicks,
            (bcRequiredForWasteElimination(wasteAmount) / bcPerTick(colony.netBCIncome())).ceilInt()
        )
    }

    fun wasteCleanedFor(bc: Float): Int {
        if (empire == null) return 0
        return (bc / empire.wasteCleanupCost()).toInt()
    }

    fun populationBoughtFor(bc: Float): Float {
        if (empire == null) return 0f
        return (bc / empire.populationBuyCost())
    }

    fun terraformingIsPossible(): Boolean {
        return colony.star.pTerraformLevel < (empire?.bestTerraformingMaxIncrease() ?: 0)
    }

    override fun calculateEconomyStep(pes: PendingEconomyStep) {
        println("--- Ticks allocated $ticksAllocated ---")
        if (empire == null) return
        var bcBudget = bcAllocFromTotal(pes.netBcWithReserveBefore)

        // 1. Waste elimination
        val newWaste = colony.generatedWaste()
        val totalWaste = colony.totalWaste()
        val cleanedWaste = min(wasteCleanedFor(bcBudget), totalWaste)
        println("pop ${colony.population} total bc ${bcBudget}, active factories ${colony.activeFactories()}")
        println("Total waste $totalWaste (${colony.generatedWaste()} + ${colony.star.pWaste})")
        println("   cleaned waste $cleanedWaste, cleaned for ${cleanedWaste * empire.wasteCleanupCost()}")
        pes.wasteChange = newWaste - cleanedWaste
        bcBudget -= cleanedWaste * empire.wasteCleanupCost()

        // TODO: Soil enrichment
        // TODO: atmospheric terraforming
        // Terraforming
        if (bcBudget > 0 && terraformingIsPossible()) {
            val tFormPrice = empire.bestTerraformingPrice().toFloat()
            val increasePerBudget = bcBudget / tFormPrice
            val realIncrease = minOf(increasePerBudget.toInt(), empire.bestTerraformingMaxIncrease() - star.pTerraformLevel)
            pes.terraformingChange = realIncrease
            bcBudget = 0f
        }
        // Pop buy
        if (bcBudget > 0) {
            val boughtPop = populationBoughtFor(bcBudget)
            pes.boughtPop = min(boughtPop, colony.star.pCurrentSizeAfterWaste() - colony.population)
            bcBudget -= pes.boughtPop * empire.populationBuyCost()
        }
        pes.unspentBc = bcBudget
    }
}
