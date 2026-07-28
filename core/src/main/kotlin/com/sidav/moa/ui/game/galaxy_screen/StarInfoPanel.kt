package com.sidav.moa.ui.game.galaxy_screen

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.kotcrab.vis.ui.VisUI
import com.sidav.moa.game.space.Star

class StarInfoPanel : Table() {

    init {
        background = VisUI.getSkin().getDrawable("window-bg")
        isVisible = false
        touchable = Touchable.enabled
        addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Empty
            }
        })
    }

    fun show(star: Star) {
        clear()

        add(Label(star.name, VisUI.getSkin()).apply { wrap = true }).width(200f).pad(10f).row()
        add(Label(star.asString(), VisUI.getSkin()).apply { wrap = true }).width(200f).pad(10f)

        isVisible = true
    }

    fun hide() {
        isVisible = false
    }
}
