package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.space.PlanetType
import com.sidav.moa.game.tech.TechField
import com.sidav.moa.game.tech.items.ship_parts.BaseShipSpecial
import com.sidav.moa.game.tech.items.ship_parts.ByShipSize
import com.sidav.moa.game.tech.items.ship_parts.ShipColonyBase

class ReserveFuelTanksTech(techLevel: Int) :
    BaseShipSpecialGivingTech<ShipReserveFuelTanks>(
        TechField.CONSTRUCTION, techLevel, "Reserve Fuel Tanks", ""
    ) {

    override val givesItems = listOf(
        ShipReserveFuelTanks(
            techLevel,
            ByShipSize(20, 100, 500, 2500),
            ByShipSize(2f, 10f, 50f, 250f)
        )
    )
}

class ShipReserveFuelTanks(
    techLevel: Int,
    baseSizes: ByShipSize<Int>,
    baseCosts: ByShipSize<Float>,
) : BaseShipSpecial("Reserve fuel tanks", techLevel, TechField.CONSTRUCTION, baseSizes, baseCosts)
