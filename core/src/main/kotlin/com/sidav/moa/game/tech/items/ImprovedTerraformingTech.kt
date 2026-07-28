package com.sidav.moa.game.tech.items

import com.sidav.moa.game.tech.TechField

class ImprovedTerraformingTech(techLevel: Int, val maxSizeIncrease: Int, val pricePerIncrease: Int) : BaseTechItem(
    TechField.PLANETOLOGY,
    techLevel,
    "Terraforming +$maxSizeIncrease"
) {}
