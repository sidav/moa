package com.sidav.moa.game.tech.items

import com.sidav.moa.game.tech.TechField

class ImprovedIndustrialTech(techLevel: Int, val factoryCost: Int) :
    BaseTechItem(TechField.CONSTRUCTION, techLevel, "Improved Industrial Tech $factoryCost") {
    val factoryCostF = factoryCost.toFloat()
}
