package com.sidav.moa.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.kotcrab.vis.ui.VisUI
import com.sidav.moa.Main
import com.sidav.moa.game.NewGame
import com.sidav.moa.ui.game.galaxy_screen.GalaxyScreen

class GalaxySizeScreen(val onSizeSelected: (width: Int, height: Int) -> Unit) : BaseScreen() {
    override fun show() {
        Gdx.input.inputProcessor = stage

        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)

        table.add(Label("Choose Galaxy Size", VisUI.getSkin())).padBottom(30f).colspan(1).row()

        listOf("Small", "Medium", "Large", "Huge").forEach { size ->
            val button = TextButton(size, VisUI.getSkin())
            button.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    println("Выбран размер: $size")
                    onSizeSelected(15, 15)
                }
            })
            table.add(button).width(300f).height(100f).pad(5f).row()
        }
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f)
        stage.act(delta)
        stage.draw()
    }
}
