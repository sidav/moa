package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.tech.TechField
import com.sidav.moa.game.tech.items.ship_parts.ByShipSize
import com.sidav.moa.game.tech.items.ship_parts.ShipEngines

class ShipEnginesTech(techLevel: Int, techId: TechId) : BaseShipPartGivingTech<ShipEngines>(
    tField = TechField.PROPULSION,
    techLevel = techLevel,
    name = "${techId.engineName} ${techId.engineFluffType}",
    description = ""
) {
    enum class TechId(val engineName: String, val engineFluffType: String) {
        RETRO("Retro", "Engines"),
        NUCLEAR("Nuclear", "Engines"),
        SUBLIGHT("Sub-Light", "Drives"),
        FUSION("Fusion", "Drives"),
        IMPULSE("Impulse", "Drives"),
        ION("Ion", "Drives"),
        ANTIMATTER("Antimatter", "Drives"),
        INTERPHASED("Interphased", "Drives"),
        HYPER("Hyper", "Drives"),
    }

    override val givesItems = when (techId) {
        TechId.RETRO -> listOf(
            ShipEngines(
                "Retro",
                techLevel,
                ByShipSize(10),
                ByShipSize(5f),
                1,
                10
            ),
        )

        else -> listOf(
            ShipEngines("Unimplemented", techLevel, ByShipSize(5), ByShipSize(5f), 1, 10)
        )
//        TechId.NUCLEAR -> TODO()
//        TechId.SUBLIGHT -> TODO()
//        TechId.FUSION -> TODO()
//        TechId.IMPULSE -> TODO()
//        TechId.ION -> TODO()
//        TechId.ANTIMATTER -> TODO()
//        TechId.INTERPHASED -> TODO()
//        TechId.HYPER -> TODO()
    }
}

