package com.sidav.moa.game.empire.race

abstract class BaseRace {
    abstract val name: String
    abstract val homeworldName: String

    fun baseBcFromPopulation(): Float {
        return 0.5f
    }

    fun populationGrowthMultiplier(): Float {
        return 1f
    }
}
