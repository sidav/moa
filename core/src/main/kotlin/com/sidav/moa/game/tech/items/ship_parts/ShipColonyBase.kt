package com.sidav.moa.game.tech.items.ship_parts

import com.sidav.moa.game.space.PlanetType
import com.sidav.moa.game.tech.TechField

class ShipColonyBase(
    label: String,
    techLevel: Int,
    baseSizes: ByShipSize<Int>,
    baseCosts: ByShipSize<Float>,
    val worstAllowedPlanetType: PlanetType
) : BaseShipSpecial (label, techLevel, TechField.PLANETOLOGY, baseSizes, baseCosts)
