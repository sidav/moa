package com.sidav.moa.controllers

import com.sidav.moa.Main
import com.sidav.moa.game.GameState
import com.sidav.moa.game.NewGame
import com.sidav.moa.ui.GalaxySizeScreen
import com.sidav.moa.ui.main_menus.MainMenuScreen

class MainMenuController(private val game: Main, private val onGameCreated: (GameState) -> Unit) {
    fun start() {
        game.setScreen(MainMenuScreen { showGalaxySizeScreen() })
    }

    private fun showGalaxySizeScreen() {
        game.setScreen(GalaxySizeScreen { width, height ->
            val gameState = NewGame.createNewGame(width, height)
            onGameCreated(gameState)
        })
    }

}
