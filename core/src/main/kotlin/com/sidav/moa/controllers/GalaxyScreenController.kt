package com.sidav.moa.controllers

import com.sidav.moa.Main
import com.sidav.moa.game.GameState
import com.sidav.moa.ui.game.galaxy_screen.GalaxyScreen

class GalaxyScreenController(
    private val game: Main,
    private val gameState: GameState,
    private val onEndTurnRequested: () -> Unit,
) {
    private val galaxyScreen = GalaxyScreen(
        gameState, onEndTurnCallback = onEndTurnRequested, onGameMenuItemSelected = ::onMenuItemSelected
    )

    fun show() {
        game.setScreen(galaxyScreen)
    }

    fun refreshColonyPanel() {
        galaxyScreen.refreshColonyPanel()
    }

    private fun onMenuItemSelected(item: GalaxyScreen.GalaxyMenuItem) {
        when (item) {
            GalaxyScreen.GalaxyMenuItem.SHIP_DESIGN -> ShipDesignFlowController(game, gameState, ::show).start()
//            GalaxyScreen.GalaxyMenuItem.TECH_TREE -> TODO()
//            GalaxyScreen.GalaxyMenuItem.GALAXY_MAP -> TODO()
//            GalaxyScreen.GalaxyMenuItem.COLONIES -> TODO()
//            GalaxyScreen.GalaxyMenuItem.PLANETS -> TODO()
            else -> {}
        }
    }
}

