package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.tech.TechField
import com.sidav.moa.util.toRoman

class ShipBeamWeaponTech(techLevel: Int, weaponId: Int) : BaseShipPartTech(
    field = TechField.PROPULSION,
    techLevel = techLevel,
    name = when (weaponId) {
        0 -> "Laser"
        1 -> "Heavy Laser"
        2 -> "Gatling Laser"
        else -> "BUG unknown beam weapon with ID $weaponId"
    },
    description = ""
)

