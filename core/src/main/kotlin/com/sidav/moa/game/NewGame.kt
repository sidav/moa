package com.sidav.moa.game

import com.sidav.moa.game.empire.Empire
import com.sidav.moa.game.empire.race.Humans
import com.sidav.moa.game.empire.race.Terrans
import com.sidav.moa.game.space.GalaxyGenerator

object NewGame {
    fun createNewGame(width: Int, height: Int): GameState {
        val galaxy = GalaxyGenerator.generateGalaxy(width, height)
        val homeworlds = galaxy.pickSpreadOutStars(galaxy.stars, 2)
        galaxy.AddEmpire(Empire(Humans()), homeworlds[0])
        galaxy.AddEmpire(Empire(Terrans()), homeworlds[1])

        val gs = GameState(galaxy)
        return gs
    }
}
