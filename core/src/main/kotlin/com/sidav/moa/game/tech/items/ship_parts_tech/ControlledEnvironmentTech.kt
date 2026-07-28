package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.space.PlanetType
import com.sidav.moa.game.tech.TechField
import com.sidav.moa.game.tech.items.ship_parts.ByShipSize
import com.sidav.moa.game.tech.items.ship_parts.ShipColonyBase

class ControlledEnvironmentTech(techLevel: Int, worstControlledEnv: PlanetType) :
    BaseShipSpecialGivingTech<ShipColonyBase>(
        TechField.PLANETOLOGY, techLevel, "Controlled ${worstControlledEnv.name} environment", ""
    ) {

    private fun namePerEnvironment(env: PlanetType): String {
        if (env == PlanetType.MINIMAL)
            return "Colony base"
        val name = when (env) {
            PlanetType.BARREN,
            PlanetType.TUNDRA,
            PlanetType.DEAD,
            PlanetType.INFERNO,
            PlanetType.TOXIC,
            PlanetType.RADIATED -> env.name
            else -> "UNKNOWN"
        }
        return "$name colony base"
    }

    private fun costPerEnvironment(env: PlanetType): Float {
        return when (env) {
            PlanetType.MINIMAL -> 350f
            PlanetType.BARREN -> 375f
            PlanetType.TUNDRA -> 400f
            PlanetType.DEAD -> 425f
            PlanetType.INFERNO -> 450f
            PlanetType.TOXIC -> 475f
            PlanetType.RADIATED -> 500f
            else -> 123f // stub
        }
    }

    override val givesItems = listOf(
        ShipColonyBase(
            namePerEnvironment(worstControlledEnv),
            techLevel,
            ByShipSize(700),
            ByShipSize(costPerEnvironment(worstControlledEnv)),
            worstControlledEnv,
        )
    )
}
