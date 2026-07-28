package com.sidav.moa.controllers

import com.sidav.moa.Main
import com.sidav.moa.game.GameState
import com.sidav.moa.game.tech.TechField
import com.sidav.moa.ui.game.PlayerEmpireEventsScreen
import com.sidav.moa.ui.game.galaxy_screen.SelectResearchScreen

class GameController(private val game: Main, private val gameState: GameState) {

    private val galaxyScreenController = GalaxyScreenController(
        game,
        gameState,
        onEndTurnRequested = ::onEndTurnRequested
    )

    fun start() {
        startTurnCycle()
    }

    private fun startTurnCycle() {
        showEmpireEventsNotificationsIfAny(::afterEmpireEventsShown)
    }

    private fun afterEmpireEventsShown() {
        val fieldsNeedingResearch = gameState.playerEmpire().techTree.fieldsWithNoOngoingResearch()
        showNextTechScreen(fieldsNeedingResearch, ::showGalaxy)
    }

    private fun showGalaxy() {
        galaxyScreenController.show()
    }

    private fun onEndTurnRequested() {
        offerResearchChoicesIfAny(::afterResearchChosen)
    }

    private fun afterResearchChosen() {
        gameState.playerEndedTurn()
        galaxyScreenController.refreshColonyPanel()
        startTurnCycle()
    }

    private fun offerResearchChoicesIfAny(onDone: () -> Unit) {
        val fieldsNeedingResearch = gameState.playerEmpire().techTree.fieldsWithNoOngoingResearch()
        showNextTechScreen(fieldsNeedingResearch, onDone)
    }

    private fun showNextTechScreen(remaining: List<TechField>, onSequenceDone: () -> Unit) {
        val playerEmpire = gameState.playerEmpire()
        if (remaining.isEmpty() || !playerEmpire.producesRPs()) {
            onSequenceDone()
            return
        }
        val tField = remaining.first()
        val choices = playerEmpire.techTree.researchableTechs(tField)
        if (choices.isEmpty()) {
            showNextTechScreen(remaining.drop(1), onSequenceDone)
            return
        }
        val techScreen = SelectResearchScreen(playerEmpire.techTree, tField, choices) { chosen ->
            playerEmpire.techTree.setCurrentResearch(chosen)
            showNextTechScreen(remaining.drop(1), onSequenceDone)
        }
        val previousScreen = game.screen
        game.setScreen(techScreen)
        previousScreen?.takeIf { it is SelectResearchScreen }?.dispose()
    }

    private fun showEmpireEventsNotificationsIfAny(onDone: () -> Unit) {
        val techTree = gameState.playerEmpire().techTree
        if (techTree.justResearchedTech.isEmpty()) {
            onDone()
            return
        }
        val previousScreen = game.screen
        game.setScreen(PlayerEmpireEventsScreen(techTree, onDone))
        previousScreen?.takeIf { it is PlayerEmpireEventsScreen }?.dispose()
    }
}
