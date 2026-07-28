package com.sidav.moa.game.colony

// This represents the SHiP, DEFense, INDustry, ECOlogy and SCIence sliders like in original MoO.
class Budget(colony: Colony) {
    private val sliders = arrayOf<ColonySpendingField>(
        ShipSpendingField(colony),
        DefenseSpendingField(colony),
        IndustrySpendingField(colony),
        EcologySpendingField(colony),
        TechSpendingField(colony)
    )

    init {
        setAllocSliderTicks(SpendingArea.IND, maxSpendingTicks)
    }

    fun shp() = sliders[SpendingArea.SHP.ordinal] as ShipSpendingField
    fun def() = sliders[SpendingArea.DEF.ordinal] as DefenseSpendingField
    fun ind() = sliders[SpendingArea.IND.ordinal] as IndustrySpendingField
    fun eco() = sliders[SpendingArea.ECO.ordinal] as EcologySpendingField
    fun sci() = sliders[SpendingArea.SCI.ordinal] as TechSpendingField

    fun get(area: SpendingArea): ColonySpendingField = sliders[area.ordinal]
    fun allocatedTicksf(area: SpendingArea): Float = sliders[area.ordinal].ticksAllocated.toFloat()
    fun setAllocSliderTicks(area: SpendingArea, value: Int) {
        if (get(area).locked) return
        get(area).ticksAllocated = value.coerceIn(0, maxSpendingTicks)
        normalizeSliders(area)
    }

    fun getLocked(area: SpendingArea): Boolean = sliders[area.ordinal].locked
    fun toggleLocked(area: SpendingArea) {
        sliders[area.ordinal].locked = !sliders[area.ordinal].locked
    }

    private fun totalAlloc(): Int =
        sliders.sumOf { it.ticksAllocated } // must always be equal to maxSpendingTicks

    private fun normalizeSliders(except: SpendingArea) {
        val total = totalAlloc()
        if (total == maxSpendingTicks) return
        else if (total < maxSpendingTicks) growSlidersToMax(total, except)
        else shrinkSlidersToMax(total, except)

        if (totalAlloc() != maxSpendingTicks) {
            println("========================")
            for (area in SpendingArea.entries) {
                println("  ${area.name}: ${get(area).ticksAllocated}")
            }
            error("Slider normalizer failed to do its job")
        }
    }

    // When total alloc is less than maximum
    private fun growSlidersToMax(total: Int, keepUnlessForced: SpendingArea) {
        var freeTicks = maxSpendingTicks - total
        val eco = eco()
        if (!eco.locked && keepUnlessForced != SpendingArea.ECO) {
            val ticksUntilMinWasteElim = eco.ticksRequiredForWasteElimination() - eco.ticksAllocated
            if (ticksUntilMinWasteElim > 0) {
                val toAdd = minOf(freeTicks, ticksUntilMinWasteElim)
                eco.ticksAllocated += toAdd
                freeTicks -= toAdd
            }
        }
        for (area in SpendingArea.NORM_GROWTH_ORDER) {
            if (getLocked(area) || area == keepUnlessForced) continue
            get(area).ticksAllocated += freeTicks
            freeTicks = 0
            break
        }
        // If everything else failed, move "keepUnlessForced" slider, no other choice here
        if (freeTicks > 0)
            get(keepUnlessForced).ticksAllocated += freeTicks
    }

    // When total alloc is more than maximum
    private fun shrinkSlidersToMax(total: Int, keepUnlessForced: SpendingArea) {
        println("I shrink sir")
        var ticksExcess = total - maxSpendingTicks
        for (area in SpendingArea.NORM_SHRINK_ORDER) {
            if (getLocked(area) || area == keepUnlessForced) continue
            val toRemove = minOf(get(area).ticksAllocated, ticksExcess)
            if (toRemove > 0) {
                println("I remove $toRemove from ${area.name} sir")
                get(area).ticksAllocated -= toRemove
                ticksExcess -= toRemove
            }
        }
        // If everything else failed, move "keepUnlessForced" slider, no other choice here
        if (ticksExcess > 0)
            get(keepUnlessForced).ticksAllocated -= ticksExcess
    }
}

enum class SpendingArea {
    SHP, DEF, IND, ECO, SCI;

    companion object {
        internal val NORM_GROWTH_ORDER = listOf(SCI, IND, DEF, SHP, ECO)
        internal val NORM_SHRINK_ORDER = listOf(SHP, DEF, SCI, IND, ECO)
    }
}
