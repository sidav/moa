package com.sidav.moa

import com.badlogic.gdx.Game
import com.kotcrab.vis.ui.VisUI
import com.sidav.moa.controllers.GameController
import com.sidav.moa.controllers.MainMenuController

class Main : Game() {
    override fun create() {
        VisUI.load()
        MainMenuController(this) { gameState ->
            GameController(this, gameState).start()
        }.start()
    }

    override fun dispose() {
        VisUI.dispose()
        super.dispose()
    }
}
