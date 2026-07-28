package com.sidav.moa.game.tech

import kotlin.math.pow

internal object MiniaturizationCalculator {

    fun calcMiniaturizedValue(value: Float, levelOfItem: Int, levelOfEmpire: Int, reductionFactor: Double): Float {
        val levelDiff = levelOfEmpire - levelOfItem
        return reduceByFactorEvery10Levels(value, levelDiff, reductionFactor)
    }

    fun calcMiniaturizedValue(value: Int, levelOfItem: Int, levelOfEmpire: Int, reductionFactor: Double): Int {
        val levelDiff = levelOfEmpire - levelOfItem
        return reduceByFactorEvery10Levels(value.toFloat(), levelDiff, reductionFactor).toInt()
    }

    private fun reduceByFactorEvery10Levels(value: Float, levelsAhead: Int, reductionFactor: Double): Float {
        val tiers = levelsAhead / 10.0 //
        return value * (1.0 - reductionFactor).pow(tiers).toFloat()
    }
}
