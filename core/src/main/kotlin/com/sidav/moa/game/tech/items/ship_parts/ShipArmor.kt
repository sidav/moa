package com.sidav.moa.game.tech.items.ship_parts

import com.sidav.moa.game.tech.TechField

class ShipArmor(
    label: String,
    techLevel: Int,
    baseSizes: ByShipSize<Int>,
    baseCosts: ByShipSize<Float>,
    val hits: ByShipSize<Int>
) : BaseShipPart(label, techLevel, TechField.CONSTRUCTION,baseSizes, baseCosts)
