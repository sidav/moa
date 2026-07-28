package com.sidav.moa.game.tech.items

import com.sidav.moa.game.tech.TechField

class ReducedIndustrialWasteTech(techLevel: Int, val wastePerFactoryPerc: Int) : BaseTechItem(
    TechField.CONSTRUCTION,
    techLevel,
    if (wastePerFactoryPerc > 0) "Reduced industrial waste $wastePerFactoryPerc" else "Industrial waste elimination"
) {
    val wastePerFactoryF = wastePerFactoryPerc.toFloat() / 100f
}
