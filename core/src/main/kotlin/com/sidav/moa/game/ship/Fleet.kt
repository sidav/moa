package com.sidav.moa.game.ship

import com.badlogic.gdx.math.Vector2
import com.sidav.moa.game.DIST_EPSILON
import com.sidav.moa.game.empire.Empire
import com.sidav.moa.game.space.Star
import com.sidav.moa.util.ceilInt
import com.sidav.moa.util.decreaseOrRemove

typealias ShipCounts = Map<ShipDesign, Int>
class Fleet(
    val owner: Empire,
    val position: Vector2,
    val ships: MutableMap<ShipDesign, Int> = mutableMapOf(),
) {
    var targetStar: Star? = null
    var orbitingStar: Star? = null

    fun isEmpty(): Boolean {
        return ships.isEmpty() || ships.values.all { it == 0 }
    }

    /** MUTATES the fleet! **/
    fun split(shipsToSplit: ShipCounts): Fleet {
        for ((design, amount) in shipsToSplit) {
            val owned = ships[design]
                ?: error("Cannot split: fleet has no ships of design '${design.name}'")
            if (owned < amount) {
                error("Cannot split: requested $amount of '${design.name}', but only $owned available")
            }
            ships.decreaseOrRemove(design, amount)
        }
        val newSplit = Fleet(owner, position.cpy(), shipsToSplit.toMutableMap())
        newSplit.orbitingStar = orbitingStar
        return newSplit
    }

    fun speed(): Float {
        return 1f // TODO
    }
}
