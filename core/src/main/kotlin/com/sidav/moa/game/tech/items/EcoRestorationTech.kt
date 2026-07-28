package com.sidav.moa.game.tech.items

import com.sidav.moa.game.tech.TechField

class EcoRestorationTech(techLevel: Int, val removedPerBc: Int) : BaseTechItem(
    TechField.PLANETOLOGY,
    techLevel,
    when(removedPerBc) {
        2 -> "Basic"
        3 -> "Improved"
        5 -> "Enhanced"
        10 -> "Advanced"
        20 -> "Complete"
        else -> error("Unknown eco restoration value: $removedPerBc")
    } + " Eco Restoration"
) {
    val wasteRemovalCostF = 1f/removedPerBc.toFloat()
}
