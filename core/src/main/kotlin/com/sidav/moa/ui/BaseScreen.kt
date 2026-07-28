package com.sidav.moa.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import ktx.app.KtxScreen

abstract class BaseScreen() : KtxScreen {
    private val virtualW = 360f
    private val virtualH = 780f
    protected val stage = Stage(ExtendViewport(virtualW, virtualH))
//    private val stage = Stage(ScreenViewport().apply {
//        unitsPerPixel = 1f / Gdx.graphics.density
//    })


    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f)
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }
    override fun hide() {}
    override fun pause() {}
    override fun resume() {}
    override fun dispose() {
        stage.dispose()
    }

    protected open fun clearScreen() {
        ScreenUtils.clear(0.05f, 0.05f, 0.05f, 1f)
    }
}
