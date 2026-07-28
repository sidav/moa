package com.sidav.moa.game.tech.items.ship_parts

import com.sidav.moa.game.tech.TechField

class ShipEcm (
    label: String,
    techLevel: Int,
    baseSizes: ByShipSize<Int>,
    baseCosts: ByShipSize<Float>,
) : BaseShipPart(label, techLevel, TechField.COMPUTERS, baseSizes, baseCosts)
