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

        for (size in MapSizes.entries) {
            table.add(TextButton(size.nameWithSize, baseSkin).apply {
                addListener(object : ClickListener() {
                    override fun clicked(event: InputEvent?, x: Float, y: Float) {
                        onSizeSelected(size.size, size.size)
                    }
                })
            }).width(300f).height(100f).pad(5f).row()
        }
    }

    private enum class MapSizes(name: String, val size: Int) {
        SMALL("Small", 15),
        MEDIUM("Medium", 20),
        LARGE("Large", 25),
        HUGE("Huge", 30);

        val nameWithSize: String = "$name (${size}x$size)"
    }
}
