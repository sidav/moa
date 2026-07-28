package com.sidav.moa.game.tech.items

import com.sidav.moa.game.space.PlanetType
import com.sidav.moa.game.tech.TechField

class ControlledEnvironmentTech(techLevel: Int, val worstControlledEnv: PlanetType) :
    BaseTechItem(
        TechField.PLANETOLOGY,
        techLevel,
        "Controlled ${worstControlledEnv.name} environment"
    ) {

    fun allowsToCol(env: PlanetType): Boolean {
        return env.betterOrEqualTo(worstControlledEnv)
    }
}
