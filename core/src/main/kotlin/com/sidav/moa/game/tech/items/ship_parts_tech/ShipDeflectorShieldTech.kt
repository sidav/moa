package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.tech.TechField
import com.sidav.moa.util.toRoman

class ShipDeflectorShieldTech(techLevel: Int, mark: Int) : BaseShipPartTech(
    TechField.FORCE_FIELDS, techLevel, name = "Class ${mark.toRoman()} Deflector Shields", description = ""
)
