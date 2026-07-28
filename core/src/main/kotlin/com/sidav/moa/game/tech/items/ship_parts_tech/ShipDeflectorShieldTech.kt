package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.tech.TechField
import com.sidav.moa.game.tech.items.ship_parts.ByShipSize
import com.sidav.moa.game.tech.items.ship_parts.ShipShield
import com.sidav.moa.util.toRoman

class ShipDeflectorShieldTech(techLevel: Int, mark: Int) : BaseShipPartGivingTech<ShipShield>(
    TechField.FORCE_FIELDS,
    techLevel,
    name = "Class ${mark.toRoman()} Deflector Shields",
    description = ""
) {
    init {
        require(mark > 0) { "Mark must be > 0" }
    }

    override val givesItems = when (mark) {
        1 -> listOf(ShipShield("Mark ${mark.toRoman()}", techLevel, ByShipSize(1), ByShipSize(5f)))
        else -> listOf(ShipShield("UNIMPLEMENTED Mark ${mark.toRoman()}", techLevel, ByShipSize(1), ByShipSize(5f)))
    }
}
