package com.sidav.moa.game.empire

import com.sidav.moa.game.empire.race.BaseRace
import com.sidav.moa.game.space.Star
import com.sidav.moa.game.tech.TechTree
import com.sidav.moa.game.tech.items.CloningTech
import com.sidav.moa.game.tech.items.EcoRestorationTech
import com.sidav.moa.game.tech.items.ImprovedIndustrialTech
import com.sidav.moa.game.tech.items.ImprovedRoboticControlsTech
import com.sidav.moa.game.tech.items.ImprovedTerraformingTech
import com.sidav.moa.game.tech.items.ReducedIndustrialWasteTech

internal const val taxYield = 0.5f // only 50% of taxed BCs make it to the empire reserve

class Empire(val race: BaseRace) {
    val colonies = mutableListOf<Star>()
    val techTree = TechTree(this)
    val taxRatePercent: Int = 0
    val globalBcReserve = 0f

    init {
        techTree.addStartingTechs()
        techTree.generateTechTree(50) // TODO: race bonus for tech probability
        println(techTree.toString())
    }

    fun onColonizeStar(star: Star) {
        if (!star.hasInhabitablePlanet())
            error("Colonizing a star with no planet!")
        if (colonies.contains(star))
            error("Star to colonize is already in the list!")
//        if (star.colony != null)
//            error("There already is a colony!")
        colonies.add(star)
    }

    fun onUncolonizeStar(star: Star) {
        colonies.remove(star)
    }

    fun nextTurn() {
        // Economy
        for (star in colonies) {
            if (star.isColonized()) {
                star.colony?.nextTurn()
            } else {
                error("Star in empire.colonies has no colony!")
            }
        }
        techTree.finishCompletedResearch()
    }

    /// Economy-related
    fun producesRPs(): Boolean {
        for (s in colonies) {
            if ((s.colony?.budget?.sci()?.ticksAllocated ?: 0) > 0)
                return true
        }
        return false
    }

    /// TECH-RELATED FUNCS
    fun factoryPrice(): Float {
        return techTree.bestTech<ImprovedIndustrialTech>()?.factoryCostF ?: 10f
    }

    fun productionPerFactory(): Float {
        return 1f // TODO: tech
    }

    /** Factories per population **/
    fun bestRoboticControls(): Float {
        return techTree.bestTech<ImprovedRoboticControlsTech>()?.factoriesPerPopF ?: 2f
    }

    fun bestTerraformingMaxIncrease(): Int {
        return techTree.bestTech<ImprovedTerraformingTech>()?.maxSizeIncrease ?: 0
    }

    fun bestTerraformingPrice(): Int {
        return techTree.bestTech<ImprovedTerraformingTech>()?.pricePerIncrease ?: 0
    }

    fun wastePerOperatingFactory(): Float {
        return techTree.bestTech<ReducedIndustrialWasteTech>()?.wastePerFactoryF ?: 1f
    }

    fun wasteCleanupCost(): Float {
        return techTree.bestTech<EcoRestorationTech>()?.wasteRemovalCostF ?: 0.5f
    }

    fun populationBuyCost(): Float {
        return techTree.bestTech<CloningTech>()?.popCostF ?: 20f
    }
}
