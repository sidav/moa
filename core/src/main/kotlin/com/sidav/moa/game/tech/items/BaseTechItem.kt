package com.sidav.moa.game.tech.items

import com.sidav.moa.game.tech.TechField
import kotlin.math.pow

abstract class BaseTechItem(val tField: TechField, val techLevel: Int, val name: String, val description: String = "") {
    // Each 5 techs are considered a subcategory
    val quintile: Int = (techLevel + 4) / 5
    val isStartingTech = techLevel == 1
}
