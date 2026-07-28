package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.tech.TechField
import com.sidav.moa.game.tech.items.ship_parts.ByShipSize
import com.sidav.moa.game.tech.items.ship_parts.ShipEcm
import com.sidav.moa.game.tech.items.ship_parts.ShipShield
import com.sidav.moa.util.toRoman

class EcmJammerTech(techLevel: Int, val mark: Int): BaseShipPartGivingTech<ShipEcm>(
    TechField.COMPUTERS,
    techLevel,
    "Ecm Jammer Mk ${mark.toRoman()}",
    ""
) {
    init {
        require(mark > 0) { "Mark must be > 0" }
    }

    override val givesItems = when (mark) {
        1 -> listOf(ShipEcm("ECM Mark ${mark.toRoman()}", techLevel, ByShipSize(1), ByShipSize(5f)))
        else -> listOf(ShipEcm("UNIMPLEMENTED ECM Mark ${mark.toRoman()}", techLevel, ByShipSize(1), ByShipSize(5f)))
    }
}
