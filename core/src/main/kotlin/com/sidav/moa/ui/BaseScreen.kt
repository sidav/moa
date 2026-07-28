package com.sidav.moa.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.kotcrab.vis.ui.VisUI
import ktx.app.KtxScreen

abstract class BaseScreen() : KtxScreen {
    private val virtualW = 360f
    private val virtualH = 780f
    protected val baseSkin = VisUI.getSkin()
    protected val stage = Stage(ExtendViewport(virtualW, virtualH))
//    private val stage = Stage(ScreenViewport().apply {
//        unitsPerPixel = 1f / Gdx.graphics.density
//    })


    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        clearScreen()
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

    private fun buildDialogOverlay(dialogBox: Table): Table {
        val overlay = Table()
        overlay.setFillParent(true)
        overlay.background = baseSkin.newDrawable("white", Color(0f, 0f, 0f, 0.6f))
        overlay.add(dialogBox)
        return overlay
    }

    fun showConfirmDialog(
        message: String,
        onYes: () -> Unit,
        onNo: () -> Unit = {}
    ) {
        lateinit var overlay: Table
        val dialogBox = Table()
        dialogBox.background = baseSkin.getDrawable("window-bg")
        dialogBox.pad(20f)
        dialogBox.add(Label(message, baseSkin)).colspan(2).padBottom(20f).row()

        val yesButton = TextButton("Yes", baseSkin)
        val noButton = TextButton("No", baseSkin)

        yesButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                overlay.remove()
                onYes()
            }
        })
        noButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                overlay.remove()
                onNo()
            }
        })

        dialogBox.add(yesButton).width(120f).height(60f).padRight(10f)
        dialogBox.add(noButton).width(120f).height(60f)

        overlay = buildDialogOverlay(dialogBox)
        stage.addActor(overlay)
    }

    fun showOkDialog(
        message: String,
        onOk: () -> Unit = {}
    ) {
        lateinit var overlay: Table
        val dialogBox = Table()
        dialogBox.background = baseSkin.getDrawable("window-bg")
        dialogBox.pad(20f)
        dialogBox.add(Label(message, baseSkin)).padBottom(20f).row()

        val okButton = TextButton("OK", baseSkin)
        okButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                overlay.remove()
                onOk()
            }
        })

        dialogBox.add(okButton).width(120f).height(60f)

        overlay = buildDialogOverlay(dialogBox)
        stage.addActor(overlay)
    }

}
