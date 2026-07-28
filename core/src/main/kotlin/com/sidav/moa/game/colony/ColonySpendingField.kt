package com.sidav.moa.game.colony;

import com.sidav.moa.util.floor

const val maxSpendingTicks = 25
const val maxSpendingTicksF = maxSpendingTicks.toFloat()

abstract class ColonySpendingField(protected val colony: Colony) {
    var ticksAllocated = 0
    var locked = false
    protected val empire = colony.empire
    protected val star = colony.star

    fun bcPerTick(totalBc: Float): Float {
        return totalBc.floor() / maxSpendingTicksF
    }

    fun bcAllocFromTotal(totalBc: Float): Float {
        return ticksAllocated * bcPerTick(totalBc)
    }

    abstract fun text(): String

    internal open fun calculateEconomyStep(pes: PendingEconomyStep) {}
}
