package com.sidav.moa.game.tech.items

import com.sidav.moa.game.space.PlanetGrowth
import com.sidav.moa.game.tech.TechField

class SoilEnrichmentTech(techLevel: Int, val allowsGrowthTo: PlanetGrowth) : BaseTechItem(
    TechField.PLANETOLOGY,
    techLevel,
    when(allowsGrowthTo) {
        PlanetGrowth.FERTILE -> "Soil Enrichment"
        PlanetGrowth.GAIA -> "Advanced Soil Enrichment"
        else -> error("Unknown SoilEnrichmentTech value: ${allowsGrowthTo.name}")
    }
) {}
