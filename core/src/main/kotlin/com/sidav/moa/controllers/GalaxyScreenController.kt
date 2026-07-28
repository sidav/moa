package com.sidav.moa.controllers

import com.sidav.moa.Main
import com.sidav.moa.game.GameState
import com.sidav.moa.ui.game.galaxy_screen.GalaxyScreen

class GalaxyScreenController(
    private val game: Main,
    private val gameState: GameState,
    private val onEndTurnRequested: () -> Unit
) {
    private val galaxyScreen = GalaxyScreen(
        gameState,
        onEndTurnCallback = onEndTurnRequested,
    )

    fun show() {
        game.setScreen(galaxyScreen)
    }

    fun refreshColonyPanel() {
        galaxyScreen.refreshColonyPanel()
    }
}

