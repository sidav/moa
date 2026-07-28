package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.tech.TechField
import com.sidav.moa.game.tech.items.ship_parts.BeamWeapon
import com.sidav.moa.game.tech.items.ship_parts.ByShipSize

class ShipBeamWeaponTech(techLevel: Int, weaponId: TechIds) : BaseShipWeaponGivingTech<BeamWeapon>(
    tField = TechField.WEAPONS,
    techLevel = techLevel,
    name = weaponId.techName,
    description = ""
) {

    enum class TechIds(val techName: String) {
        LASER("Lasers"),
        GATLING_LASER("Gatling Laser")
    }

    override val givesItems = when (weaponId) {
        TechIds.LASER -> listOf(
            BeamWeapon(
                "Laser",
                techLevel = techLevel,
                ByShipSize(1, 1, 1, 1), // TODO
                ByShipSize(0f, 0f, 0f, 0f), // TODO
            ),
            BeamWeapon(
                "Heavy Laser",
                techLevel = techLevel,
                ByShipSize(1, 1, 1, 1), // TODO
                ByShipSize(0f, 0f, 0f, 0f), // TODO
            ),
        )

        TechIds.GATLING_LASER -> listOf(
            BeamWeapon(
                "Gatling Laser",
                techLevel = techLevel,
                ByShipSize(1, 1, 1, 1), // TODO
                ByShipSize(0f, 0f, 0f, 0f), // TODO
            ),
        )
    }
}

