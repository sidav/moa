package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.tech.TechField
import com.sidav.moa.util.toRoman

class ShipArmorTech(techLevel: Int, mark: Int) : BaseShipPartTech(
    field = TechField.CONSTRUCTION,
    techLevel = techLevel,
    name = when (mark) {
        1 -> "Duralloy armor"
        2 -> "Zortium armor"
        3 -> "Andrium armor"
        4 -> "Tritanium armor"
        5 -> "Adamantium armor"
        6 -> "Neutronium armor"
        else -> "BUG unknown armor mark $mark"
    },
    description = ""
)

