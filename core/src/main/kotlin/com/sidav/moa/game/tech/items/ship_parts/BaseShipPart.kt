package com.sidav.moa.game.tech.items.ship_parts

import com.sidav.moa.game.ship.ShipSize
import com.sidav.moa.game.tech.MiniaturizationCalculator
import com.sidav.moa.game.tech.TechField

abstract class BaseShipPart(
    val label: String,
    val techLevel: Int, // It is needed only for miniaturization calc
    val techField: TechField,
    val baseSizes: ByShipSize<Int>,
    val baseCosts: ByShipSize<Float>,
    val powerConsumption: ByShipSize<Int> = ByShipSize(1) // TODO: make abstract
) {

    protected open val sizeReductionFactor = 0.25
    protected open val priceReductionFactor = 0.50

    open fun miniaturizedSize(hullSize: ShipSize, empireTechLevel: Int): Int {
        return MiniaturizationCalculator.calcMiniaturizedValue(
            baseSizes[hullSize], techLevel, empireTechLevel, sizeReductionFactor
        )
    }

    open fun miniaturizedCost(hullSize: ShipSize, empireTechLevel: Int): Float {
        val levelDiff = empireTechLevel - techLevel
        return MiniaturizationCalculator.calcMiniaturizedValue(
            baseCosts[hullSize], techLevel, empireTechLevel, priceReductionFactor
        )
    }
}

abstract class BaseShipWeapon(
    label: String,
    techLevel: Int,
    baseSizes: ByShipSize<Int>,
    baseCosts: ByShipSize<Float>
) : BaseShipPart(label, techLevel, TechField.WEAPONS, baseSizes, baseCosts) {
    final override val sizeReductionFactor = 0.50
}

abstract class BaseShipSpecial(
    label: String,
    techLevel: Int,
    techField: TechField,
    baseSizes: ByShipSize<Int>,
    baseCosts: ByShipSize<Float>
) : BaseShipPart(label, techLevel, techField, baseSizes, baseCosts)
