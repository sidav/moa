package com.sidav.moa.ui.game.galaxy_screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.kotcrab.vis.ui.VisUI
import com.sidav.moa.game.tech.TechField
import com.sidav.moa.game.tech.TechTree
import com.sidav.moa.game.tech.items.BaseTechItem
import com.sidav.moa.ui.BaseScreen

class SelectResearchScreen(
    private val techTree: TechTree,
    private val field: TechField,
    private val choices: List<BaseTechItem>,
    private val onChosen: (BaseTechItem) -> Unit
) : BaseScreen() {

    override fun show() {
        Gdx.input.inputProcessor = stage

        val root = Table()
        root.setFillParent(true)
        stage.addActor(root)

        root.add(Label("Choose ${field.name} research", VisUI.getSkin())).padBottom(20f).row()

        for (tech in choices) {
            val button = TextButton("${tech.name} (${techTree.techResearchCost(tech)}RP)", VisUI.getSkin())
            button.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    onChosen(tech)
                }
            })
            root.add(button).width(300f).height(75f).padBottom(10f).row()
        }
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.05f, 0.05f, 0.05f, 1f)
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
