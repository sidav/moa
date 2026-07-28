package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.tech.TechField
import com.sidav.moa.game.tech.items.ship_parts.BaseShipPart
import com.sidav.moa.game.tech.items.ship_parts.ByShipSize
import com.sidav.moa.game.tech.items.ship_parts.ShipArmor

class ShipArmorTech(techLevel: Int, mark: TechIds) : BaseShipPartGivingTech<ShipArmor>(
    tField = TechField.CONSTRUCTION,
    techLevel = techLevel,
    name = "${mark.materialName} armor",
    description = ""
) {

    enum class TechIds(val materialName: String) {
        TITANIUM("Titanium"),
        DURALLOY("Duralloy"),
        ZORTIUM("Zortium"),
        ANDRIUM("Andrium"),
        TRITANIUM("Tritanium"),
        ADAMANTUM("Adamantium"),
        NEUTRONIUM("Neutronium"),
    }

    override val givesItems = when (mark) {
        TechIds.TITANIUM -> listOf<ShipArmor>(
            ShipArmor(
                mark.materialName,
                techLevel,
                ByShipSize(0, 0, 0, 0),
                ByShipSize(0f, 0f, 0f, 0f),
                ByShipSize(3, 18, 100, 600)
            ),
            ShipArmor(
                "${mark.materialName} II",
                techLevel,
                ByShipSize(14, 80, 400, 2000),
                ByShipSize(2f, 10f, 50f, 250f),
                ByShipSize(4, 27, 150, 900)
            )
        )
//        TechIds.DURALLOY -> TODO()
//        TechIds.ZORTIUM -> TODO()
//        TechIds.ANDRIUM -> TODO()
//        TechIds.TRITANIUM -> TODO()
//        TechIds.ADAMANTUM -> TODO()
//        TechIds.NEUTRONIUM -> TODO()

        // TODO: delete this
        else -> listOf<ShipArmor>(
            ShipArmor(
                "NOT IMPLEMENTED ARMOR",
                techLevel,
                ByShipSize(0),
                ByShipSize(0f),
                ByShipSize(3, 18, 100, 600)
            ),
            ShipArmor(
                "NOT IMPLEMENTED ARMOR II",
                techLevel,
                ByShipSize(14, 80, 400, 2000),
                ByShipSize(2f, 10f, 50f, 250f),
                ByShipSize(4, 27, 150, 900)
            )
        )
    }
}


