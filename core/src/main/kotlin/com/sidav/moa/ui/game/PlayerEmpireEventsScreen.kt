package com.sidav.moa.ui.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.kotcrab.vis.ui.VisUI
import com.sidav.moa.game.tech.TechTree
import com.sidav.moa.ui.BaseScreen

class PlayerEmpireEventsScreen(
    private val techTree: TechTree,
    private val onClosed: () -> Unit
) : BaseScreen() {

    override fun show() {
        Gdx.input.inputProcessor = stage

        val root = Table()
        root.setFillParent(true)
        stage.addActor(root)

        root.add(Label("Empire events this turn:", VisUI.getSkin())).padBottom(20f).row()

        for (newTech in techTree.justResearchedTech) {
            root.add(
                Label("Research complete: ${newTech.name}", VisUI.getSkin())
                    .also { it.wrap = true }
            ).width(200f).height(75f).row()
        }

        root.add(
            TextButton("OK", VisUI.getSkin())
                .also {
                    it.addListener(object : ClickListener() {
                        override fun clicked(event: InputEvent?, x: Float, y: Float) {
                            onClosed()
                        }
                    })

                }
        ).width(300f).height(75f).padBottom(10f).row()
    }

    override fun render(delta: Float) {
        clearScreen()
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.dispose()
    }
}
