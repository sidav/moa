package com.sidav.moa.game.tech.items.ship_parts

import com.sidav.moa.game.tech.TechField

class ShipEngines(
    label: String,
    techLevel: Int,
    baseSizes: ByShipSize<Int>,
    baseCosts: ByShipSize<Float>,
    val warpSpeed: Int,
    val powerOutput: Int,
) : BaseShipPart(
    label,
    techLevel,
    TechField.PROPULSION,
    baseSizes,
    baseCosts,
    ByShipSize(0)) {

    val maxManeuverability: Int get() = warpSpeed
}
