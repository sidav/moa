package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.tech.TechField
import com.sidav.moa.util.toRoman

class ShipEnginesTech(techLevel: Int, mark: Int) : BaseShipPartTech(
    field = TechField.PROPULSION,
    techLevel = techLevel,
    name = when (mark) {
        1 -> "Retro Engines"
        2 -> "Nuclear Engines"
        3 -> "Sub-Light Drives"
        4 -> "Fusion Drives"
        5 -> "Impulse Drives"
        6 -> "Ion Drives"
        7 -> "Anti Matter Drives"
        8 -> "Inter-Phased Drives"
        9 -> "Hyper Drives"
        else -> "BUG unknown engines mark $mark"
    },
    description = ""
)

