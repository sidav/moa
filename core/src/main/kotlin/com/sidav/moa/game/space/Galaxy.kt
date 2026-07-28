package com.sidav.moa.game.space

import com.sidav.moa.game.colony.Colony
import com.sidav.moa.game.empire.Empire
import kotlin.random.Random

class Galaxy(val width: Int, val height: Int) {
    val stars = mutableListOf<Star>()
    val empires = mutableListOf<Empire>()

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
