package com.sidav.moa.game.tech.items

import com.sidav.moa.game.tech.TechField

class CloningTech(techLevel: Int, val popCost: Int) : BaseTechItem(
    TechField.PLANETOLOGY,
    techLevel,
    when(popCost) {
        10 -> "Cloning"
        5 -> "Advanced Cloning"
        else -> error("Unknown CloningTech value: $popCost")
    }
) {
    val popCostF = popCost.toFloat()
}

