package com.sidav.moa.game

import com.badlogic.gdx.graphics.Color
import com.sidav.moa.game.empire.Empire
import com.sidav.moa.game.empire.race.Humans
import com.sidav.moa.game.empire.race.Terrans
import com.sidav.moa.game.space.GalaxyGenerator

object NewGame {
    private val playerColors = arrayOf(
        Color.BLUE,
        Color.RED,
        Color.YELLOW,
        Color.GREEN
    )

    fun createNewGame(width: Int, height: Int): GameState {
        val galaxy = GalaxyGenerator.generateGalaxy(width, height)
        val homeworlds = galaxy.pickSpreadOutStars(galaxy.stars, 2)
        galaxy.AddEmpire(Empire(Humans(), playerColors[0]), homeworlds[0])
        println(galaxy.empires.first().techTree.toString()) // TODO: remove this
        galaxy.AddEmpire(Empire(Terrans(), playerColors[1]), homeworlds[1])

        val gs = GameState(galaxy)
        return gs
    }
}
