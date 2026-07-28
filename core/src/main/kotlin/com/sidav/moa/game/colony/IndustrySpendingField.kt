package com.sidav.moa.game.colony

import com.sidav.moa.util.toFixed1

class IndustrySpendingField(colony: Colony) : ColonySpendingField(colony) {
    private var ongoingUpgradeBc: Float = 0f

    override fun text(): String {
        val virtualEconomyStep = PendingEconomyStep(
            colony.population,
            colony.netBCIncome(),
            0f,
            colony.star.pWaste,
            colony.generatedWaste()
        )
        calculateEconomyStep(virtualEconomyStep)
        return when {
            virtualEconomyStep.roboticControlsBeingImproved -> "UPGR"
            virtualEconomyStep.newFactories > 0 -> "${virtualEconomyStep.newFactories.toFixed1()}/y"
            maxFactoriesReached() -> "MAX"
            else -> "NONE"
        }
    }

    fun upgradeRoboticControlsNeeded(): Boolean {
        return colony.currRoboticControls < (colony.empire?.bestRoboticControls() ?: 0f)
    }

    fun priceToUpgradeAllFactoriesControls(): Float {
        return colony.factories * (colony.empire?.factoryPrice() ?: error("No empire for upgrading")) / 2f
    }

    fun maxFactoriesReached(): Boolean =
        colony.maxFactoriesForPopulation(colony.population) <= colony.factories

    override fun calculateEconomyStep(pes: PendingEconomyStep) {
        if (ticksAllocated == 0 || empire == null) return
        var bcBudget = bcAllocFromTotal(pes.netBcWithReserveBefore)
        // TODO: refit factories from another race

        // Upgrade robotic controls
        if (upgradeRoboticControlsNeeded()) {
            val price = priceToUpgradeAllFactoriesControls()
            val spendingBc = minOf(price - ongoingUpgradeBc, bcBudget)
            if (spendingBc > 0) {
                pes.roboticControlsBeingImproved = true
                ongoingUpgradeBc += spendingBc
                bcBudget -= spendingBc
                if (ongoingUpgradeBc >= price) {
                    ongoingUpgradeBc = 0f
                    pes.roboticControlsImproveFinished = true
                }
            }
        }
        // Build new
        if (bcBudget > 0 && !maxFactoriesReached()) {
            val price = empire.factoryPrice()
            val maxFcts = colony.maxFactoriesForPopulation(pes.popBefore)
            val fctsPerBudget = bcBudget / price
            val actualNewFcts = minOf(fctsPerBudget, maxFcts - colony.factories)
            bcBudget -= actualNewFcts * price
            pes.newFactories = actualNewFcts
            pes.unspentBc += bcBudget
        }
    }
}
