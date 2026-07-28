package com.sidav.moa.game

import com.sidav.moa.game.empire.Empire
import com.sidav.moa.game.space.Galaxy

class GameState(val galaxy: Galaxy) {
    fun playerEmpire(): Empire = galaxy.empires.first()
    var currentTurn = 1

    fun playerEndedTurn() {
        for (e in galaxy.empires) {
            e.nextTurn()
        }
        currentTurn++
    }
}

