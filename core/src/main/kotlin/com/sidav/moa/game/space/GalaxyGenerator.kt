package com.sidav.moa.game.space

import com.badlogic.gdx.math.Vector2
import com.sidav.moa.util.RandomUtil
import com.sidav.moa.util.roundToNearestFiveF
import kotlin.random.Random

object GalaxyGenerator {
    val shuffledStarNamesList: List<String> = STAR_NAMES.shuffled()

    fun generateGalaxy(w: Int, h: Int): Galaxy {
        val galaxy = Galaxy(w, h)
        // Place nebulae TODO
        // Place all stars
        val desiredStars = w / 5 * h / 5
        (0..desiredStars).forEach { _ ->
            addRandomStarToGalaxy(galaxy)
        }
        // Add Orion TODO
        // Place homeworlds TODO

        return galaxy
    }

    private fun addRandomStarToGalaxy(g: Galaxy): Boolean {
        val minDistToAnotherStar = 3
        val minDistFromPreviousStar = 10f

        val prevStar = g.stars.lastOrNull()
        var coordsSet = false
        var coords = Vector2(0f, 0f)

        for (i in 0..10000) {
            coords.x = Random.nextInt(g.width).toFloat()
            coords.y = Random.nextInt(g.height).toFloat()

            val tooCloseToPrev =
                prevStar != null && coords.dst2(prevStar.position) < minDistFromPreviousStar * minDistFromPreviousStar

            val tooCloseToOther = g.stars.any {
                coords.dst2(it.position) < minDistToAnotherStar * minDistToAnotherStar
            }

            if (!tooCloseToPrev && !tooCloseToOther) {
                coordsSet = true
                break
            }
        }

        if (coordsSet) {
            val thisStarNumber = g.stars.count()
            val nameForStar =
                "${shuffledStarNamesList[thisStarNumber % shuffledStarNamesList.count()]} $thisStarNumber"
            val typeForStar = RandomUtil.weightedRandom(
                listOf(
                    StarType.RED to StarType.RED.probabilityWght,
                    StarType.GREEN to StarType.GREEN.probabilityWght,
                    StarType.YELLOW to StarType.YELLOW.probabilityWght,
                    StarType.BLUE to StarType.BLUE.probabilityWght,
                    StarType.WHITE to StarType.WHITE.probabilityWght,
                    StarType.NEUTRON to StarType.NEUTRON.probabilityWght,
                )
            )

            val newStar = Star(
                nameForStar, typeForStar, coords
            )
            generatePlanetOnStar(newStar)

            g.stars.add(newStar)
            return true
        }
        return false
    }

    private fun generatePlanetOnStar(star: Star) {
        val planetTypeForStar = RandomUtil.weightedRandom(star.sType.planetTypeWeights)
        star.planetType = planetTypeForStar
        val baseMaxPop = when (star.planetType) {
            PlanetType.TERRAN -> Random.nextInt(80, 100)
            PlanetType.JUNGLE -> Random.nextInt(75, 90)
            PlanetType.OCEAN -> Random.nextInt(65, 85)
            PlanetType.ARID -> Random.nextInt(55, 75)
            PlanetType.STEPPE -> Random.nextInt(45, 65)
            PlanetType.DESERT -> Random.nextInt(35, 55)
            PlanetType.MINIMAL, PlanetType.BARREN -> Random.nextInt(30, 50)
            PlanetType.TUNDRA, PlanetType.DEAD -> Random.nextInt(20, 50)
            PlanetType.INFERNO, PlanetType.TOXIC, PlanetType.RADIATED -> Random.nextInt(10, 40)
            PlanetType.NOT_INHABITABLE -> 10
        }
        var pBaseSize= baseMaxPop.roundToNearestFiveF()
        if (star.planetType == PlanetType.NOT_INHABITABLE) return

        //////////////////////////////
        // Generate planet special
        var pSpecial = PlanetSpecial.NONE
        if (star.planetType.worseOrEqualTo(PlanetType.STEPPE)) {
            var poorRoll =
                rollWithStarModifier(star, mapOf(StarType.RED to -4, StarType.GREEN to -2))
            if (poorRoll <= 2) pSpecial = PlanetSpecial.POOR
            poorRoll =
                rollWithStarModifier(star, mapOf(StarType.RED to -4, StarType.GREEN to -2))
            if (poorRoll <= 5) pSpecial = PlanetSpecial.ULTRAPOOR
        }
        var richRoll = rollWithStarModifier(star, mapOf(StarType.BLUE to -2, StarType.NEUTRON to -5))
        if (star.planetType.howBetterIsFrom(PlanetType.STEPPE) > richRoll)
            pSpecial = PlanetSpecial.RICH
        richRoll = rollWithStarModifier(star, mapOf(StarType.BLUE to -2, StarType.NEUTRON to -5))
        if (richRoll < 6)
            pSpecial = PlanetSpecial.ULTRARICH

        if (
            star.planetType.betterOrEqualTo(PlanetType.MINIMAL) &&
            star.planetType.worseOrEqualTo(PlanetType.OCEAN) &&
            pSpecial == PlanetSpecial.NONE &&
            Random.nextInt(10) == 1
        )
            pSpecial = PlanetSpecial.ARTIFACTS

        star.planetSpecial = pSpecial

        ///////////////////////
        // Roll for asteroids
        var asteroidsRoll = Random.nextInt(1, 100)
        if (star.planetSpecial == PlanetSpecial.ULTRARICH) {
            asteroidsRoll -= 20
        } else if (star.planetSpecial == PlanetSpecial.RICH) {
            asteroidsRoll -= 10
        }
        asteroidsRoll -= 15 - star.planetType.howBetterIsFrom(PlanetType.TERRAN)
        star.planetAsteroids = when {
            asteroidsRoll < 15 -> PlanetAsteroids.MANY
            asteroidsRoll < 40 -> PlanetAsteroids.SOME
            else -> PlanetAsteroids.NONE
        }

        //////////////////////
        // Roll for fertility
        var planetGrowth = PlanetGrowth.NORMAL
        if (star.planetType.worseOrEqualTo(PlanetType.BARREN))
            star.planetGrowth = PlanetGrowth.HOSTILE
        else if (star.planetType.betterOrEqualTo(PlanetType.STEPPE) && Random.nextInt(12) == 0) {
            star.planetGrowth = PlanetGrowth.FERTILE
            pBaseSize *= 1.25f
        }
        star.setPlanetBaseSize(pBaseSize)
    }

    private fun rollWithStarModifier(star: Star, modifiers: Map<StarType, Int>): Int {
        val roll = Random.nextInt(20) + 1
        return roll + (modifiers[star.sType] ?: 0)
    }
}
