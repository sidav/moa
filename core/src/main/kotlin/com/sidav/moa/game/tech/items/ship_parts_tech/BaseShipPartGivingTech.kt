package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.tech.TechField
import com.sidav.moa.game.tech.items.BaseTechItem
import com.sidav.moa.game.tech.items.ship_parts.BaseShipPart
import com.sidav.moa.game.tech.items.ship_parts.BaseShipSpecial
import com.sidav.moa.game.tech.items.ship_parts.BaseShipWeapon

abstract class BaseShipPartGivingTech<T : BaseShipPart>(tField: TechField, techLevel: Int, name: String, description: String) :
    BaseTechItem(tField, techLevel, name, description) {

    abstract val givesItems: List<T>

    fun firstGivenItem(): T {
        return givesItems.first()
    }
}

// Needed for aggregation only
abstract class BaseShipWeaponGivingTech<T: BaseShipWeapon>(tField: TechField, techLevel: Int, name: String, description: String) :
    BaseShipPartGivingTech<T>(tField, techLevel, name, description)

// Needed for aggregation only
abstract class BaseShipSpecialGivingTech<T: BaseShipSpecial>(tField: TechField, techLevel: Int, name: String, description: String) :
    BaseShipPartGivingTech<T>(tField, techLevel, name, description)
