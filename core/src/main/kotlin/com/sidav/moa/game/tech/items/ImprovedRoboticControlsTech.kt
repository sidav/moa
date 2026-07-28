package com.sidav.moa.game.tech.items

import com.sidav.moa.game.tech.TechField

class ImprovedRoboticControlsTech(techLevel: Int, val factoriesPerPop: Int) : BaseTechItem(
    TechField.COMPUTERS,
    techLevel,
    "Improved Robotic Controls $factoriesPerPop"
) {
    val factoriesPerPopF = factoriesPerPop.toFloat()
}
