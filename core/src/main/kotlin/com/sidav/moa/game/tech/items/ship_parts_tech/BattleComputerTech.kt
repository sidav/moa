package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.tech.TechField
import com.sidav.moa.util.toRoman

class BattleComputerTech(techLevel: Int, val mark: Int): BaseShipPartTech(
    TechField.COMPUTERS,
    techLevel,
    "Battle Computer Mk ${mark.toRoman()}",
    ""
) {
}

