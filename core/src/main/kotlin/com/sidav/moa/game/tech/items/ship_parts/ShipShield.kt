package com.sidav.moa.game.tech.items.ship_parts

import com.sidav.moa.game.tech.TechField

class ShipShield (
    label: String,
    techLevel: Int,
    baseSizes: ByShipSize<Int>,
    baseCosts: ByShipSize<Float>,
) : BaseShipPart(label, techLevel, TechField.FORCE_FIELDS, baseSizes, baseCosts)
