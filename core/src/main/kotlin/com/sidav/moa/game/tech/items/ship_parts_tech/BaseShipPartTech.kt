package com.sidav.moa.game.tech.items.ship_parts_tech

import com.sidav.moa.game.tech.TechField
import com.sidav.moa.game.tech.items.BaseTechItem

abstract class BaseShipPartTech(field: TechField, techLevel: Int, name: String, description: String) :
    BaseTechItem(field, techLevel, name, description) {
}

abstract class BaseShipWeaponTech(field: TechField, techLevel: Int, name: String, description: String) :
    BaseTechItem(field, techLevel, name, description) {
}
