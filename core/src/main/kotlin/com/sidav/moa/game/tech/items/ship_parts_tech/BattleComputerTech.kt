package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.tech.TechField
import com.sidav.moa.game.tech.items.ship_parts.ByShipSize
import com.sidav.moa.game.tech.items.ship_parts.ShipComputer
import com.sidav.moa.game.tech.items.ship_parts.ShipEcm
import com.sidav.moa.util.toRoman

class BattleComputerTech(techLevel: Int, val mark: Int): BaseShipPartGivingTech<ShipComputer>(
    TechField.COMPUTERS,
    techLevel,
    "Battle Computer Mk ${mark.toRoman()}",
    ""
) {
    init {
        require(mark > 0) { "Mark must be > 0" }
    }

    override val givesItems = when (mark) {
        1 -> listOf(ShipComputer("Mark ${mark.toRoman()}", techLevel, ByShipSize(1), ByShipSize(5f)))
        else -> listOf(ShipComputer("UNIMPLEMENTED Mark ${mark.toRoman()}", techLevel, ByShipSize(1), ByShipSize(5f)))
    }
}

