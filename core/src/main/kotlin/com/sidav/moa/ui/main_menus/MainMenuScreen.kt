package com.sidav.moa.ui.main_menus

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.kotcrab.vis.ui.VisUI
import com.sidav.moa.Main
import com.sidav.moa.ui.BaseScreen
import com.sidav.moa.ui.GalaxySizeScreen

class MainMenuScreen(val onNewGameRequested: () -> Unit) : BaseScreen() {
    override fun show() {
        Gdx.input.inputProcessor = stage

        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)

        table.add(Label("Master of Orion Clone", VisUI.getSkin())).row()

        val newGameButton = TextButton("New Game", VisUI.getSkin())

        newGameButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                onNewGameRequested()
            }
        })

        table.add(newGameButton).width(300f).height(80f).padBottom(20f).row()
    }

    override fun render(delta: Float) {
        clearScreen()
        stage.act(delta)
        stage.draw()
    }
}
