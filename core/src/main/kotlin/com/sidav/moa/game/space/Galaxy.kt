package com.sidav.moa.game.space

import com.badlogic.gdx.math.Vector2
import com.sidav.moa.game.DIST_EPSILON
import com.sidav.moa.game.colony.Colony
import com.sidav.moa.game.empire.Empire
import com.sidav.moa.game.ship.Fleet
import com.sidav.moa.game.ship.ShipCounts
import com.sidav.moa.util.ceilInt
import com.sidav.moa.util.epsilonEquals
import com.sidav.moa.util.epsilonLessOrEquals
import kotlin.random.Random

class Galaxy(val width: Int, val height: Int) {
    val stars = mutableListOf<Star>()
    val empires = mutableListOf<Empire>()
    private val _fleets = mutableListOf<Fleet>()
    val fleets: List<Fleet> get() = _fleets

    fun AddEmpire(empire: Empire, homeworld: Star) {
        empires.add(empire)
        homeworld.name = empire.race.homeworldName
        homeworld.sType = StarType.YELLOW
        homeworld.planetType = PlanetType.TERRAN
        homeworld.planetSpecial = PlanetSpecial.NONE
        homeworld.planetGrowth = PlanetGrowth.NORMAL
        homeworld.planetAsteroids = PlanetAsteroids.NONE
        homeworld.setPlanetBaseSize(100f)
        homeworld.colony = Colony(
            empire, homeworld, homeworld.pCurrentSizeNoWaste() / 2, 30f
        )
        empire.onColonizeStar(homeworld)
        addFleet(
            Fleet(
                empire,
                homeworld.position,
                mutableMapOf(empire.shipDesigns[0]!! to 1, empire.shipDesigns[1]!! to 2),
            )
        )
    }

    fun addFleet(fleet: Fleet) {
        fleet.orbitingStar = getStarAtCoords(fleet.position)
        fleet.owner.fleets.add(fleet)
        _fleets.add(fleet)
    }

    fun removeFleet(fleet: Fleet) {
        var removed = _fleets.remove(fleet)
        check(removed) { "Tried to remove a fleet that wasn't in the list" }
        removed = fleet.owner.fleets.remove(fleet)
        check(removed) { "Tried to remove a fleet that wasn't in the empire" }
    }

    fun moveFleets() {
        for (fleet in _fleets) {
            val target = fleet.targetStar ?: continue
            fleet.orbitingStar = null
            val speed = fleet.speed()
            val toTarget = target.position.cpy().sub(fleet.position)
            println("ToTarget: $toTarget")
            if (toTarget.len().epsilonLessOrEquals(speed, DIST_EPSILON)) {
                fleet.position.set(target.position)
                fleet.orbitingStar = target
                fleet.targetStar = null
            } else {
                fleet.position.add(toTarget.setLength(speed))
            }
        }
    }

    fun fleetETAToStar(fleet: Fleet, star: Star): Int {
        val speed = fleet.speed()
        val dst = fleet.position.dst(star.position)
        return (dst / speed).ceilInt()
    }

    fun splitAndSendFleet(fleet: Fleet, amounts: ShipCounts, targetStar: Star) {
        val newFleet = fleet.split(amounts)
        newFleet.targetStar = targetStar
        addFleet(newFleet)
        if (fleet.isEmpty()) removeFleet(fleet)
    }

    fun getStarAtCoords(pos: Vector2): Star? {
        for (star in stars)
            if (star.position.epsilonEquals(pos, DIST_EPSILON))
                return star
        return null
    }

    // Can be used for homeworlds placement
    fun pickSpreadOutStars(stars: List<Star>, count: Int): List<Star> {
        if (count >= stars.size) return stars

        val selected = mutableListOf<Star>()
        val remaining = stars.toMutableList()

        // Start with random star
        selected.add(remaining.removeAt(Random.nextInt(remaining.size)))

        while (selected.size < count) {
            // Find distance for closest already selected star, and pick one with maximum dist
            val next = remaining.maxByOrNull { candidate ->
                selected.minOf { picked -> candidate.position.dst2(picked.position) }
            }!!

            selected.add(next)
            remaining.remove(next)
        }

        return selected
    }
}
