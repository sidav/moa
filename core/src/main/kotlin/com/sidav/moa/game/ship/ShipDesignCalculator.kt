package com.sidav.moa.game.ship

import com.sidav.moa.game.empire.Empire
import com.sidav.moa.game.tech.items.ship_parts.BaseShipPart

object ShipDesignCalculator {
    fun updateDesignStats(design: ShipDesign, owner: Empire) {
        // First, recalculate needed power stuff, as engines amount may be changed
        design.recalcPowerAndSetEnginesAmount()

        // Calc cost and size
        val hSize = design.hullSize
        design.totalSpace = owner.techTree.actualShipSpace(design.hullSize)
        var occupiedSpace =
                miniaturizedPartSize(design.computer, hSize, owner) +
                miniaturizedPartSize(design.shield, hSize, owner) +
                miniaturizedPartSize(design.ecm, hSize, owner) +
                miniaturizedPartSize(design.armor, hSize, owner) +
                design.enginesAmount * miniaturizedPartSize(design.engine, hSize, owner)

        var totalCost =
                miniaturizedPartCost(design.computer, hSize, owner) +
                miniaturizedPartCost(design.shield, hSize, owner) +
                miniaturizedPartCost(design.ecm, hSize, owner) +
                miniaturizedPartCost(design.armor, hSize, owner) +
                design.enginesAmount * miniaturizedPartCost(design.engine, hSize, owner)


        for (w in design.weapons) {
            if (w == null) continue
            occupiedSpace += w.count * miniaturizedPartSize(w.item, hSize, owner)
            totalCost += w.count * miniaturizedPartCost(w.item, hSize, owner)
        }

        for (s in design.specials) {
            if (s == null) continue
            occupiedSpace += miniaturizedPartSize(s, hSize, owner)
            totalCost += miniaturizedPartCost(s, hSize, owner)
        }

        design.occupiedSpace = occupiedSpace
        design.cost = totalCost
    }

    fun miniaturizedPartCost(part: BaseShipPart?, hullSize: ShipSize, owner: Empire): Float {
        if (part == null) return 0f
        val tLevel = owner.techTree.techLevelInField(part.techField)
        return part.miniaturizedCost(hullSize, tLevel)
    }

    fun miniaturizedPartSize(part: BaseShipPart?, hullSize: ShipSize, owner: Empire): Int {
        if (part == null) return 0
        val tLevel = owner.techTree.techLevelInField(part.techField)
        return part.miniaturizedSize(hullSize, tLevel)
    }
}
