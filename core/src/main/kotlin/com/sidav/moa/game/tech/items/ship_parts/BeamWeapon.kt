package com.sidav.moa.game.tech.items.ship_parts

class BeamWeapon(
    label: String,
    techLevel: Int,
    baseSizes: ByShipSize<Int>,
    baseCosts: ByShipSize<Float>
) : BaseShipWeapon(label, techLevel, baseSizes, baseCosts) {}
