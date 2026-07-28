package com.sidav.moa.game.colony

import kotlin.math.roundToInt

class TechSpendingField(colony: Colony) : ColonySpendingField(colony) {
    override fun text(): String {
        val bcAlloc = bcAllocFromTotal(colony.netBCIncome()).roundToInt()
        if (bcAlloc > 1) {
            return "${bcAlloc}RP"
        }
        return "NONE"
    }

    override fun calculateEconomyStep(pes: PendingEconomyStep) {
        if (ticksAllocated == 0 || empire == null) return
        val totalBc = bcAllocFromTotal(pes.netBcWithReserveBefore)
        pes.newRp = totalBc
    }
}
