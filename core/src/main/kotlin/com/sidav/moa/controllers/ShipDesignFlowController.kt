package com.sidav.moa.controllers

import com.sidav.moa.Main
import com.sidav.moa.game.GameState
import com.sidav.moa.game.ship.ShipDesign
import com.sidav.moa.ui.game.ship_design.EditDesignScreen
import com.sidav.moa.ui.game.ship_design.PlayerDesignsScreen

class ShipDesignFlowController(
    private val game: Main, private val gameState: GameState, val onFinished: () -> Unit
) {
    fun start() {
        game.setScreen(
            PlayerDesignsScreen(gameState.playerEmpire(), ::openDesign, onFinished)
        )
    }

    fun openDesign(selectedDesign: ShipDesign, designIndex: Int) {
        game.setScreen(
            EditDesignScreen(
                gameState.playerEmpire(),
                selectedDesign,
                { start() },
                { draft -> saveDesignDraftIfAny(draft, designIndex); start() })
        )
    }

    fun saveDesignDraftIfAny(draft: ShipDesign?, designIndex: Int) {
        draft?.let { gameState.playerEmpire().shipDesigns[designIndex] = draft }
    }
}
