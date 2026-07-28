package com.sidav.moa.ui.game.ship_design

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.sidav.moa.game.empire.Empire
import com.sidav.moa.game.ship.ShipDesign
import com.sidav.moa.ui.BaseScreen

class PlayerDesignsScreen(
    val playerEmpire: Empire,
    val onDesignSelected: (selectedDesign: ShipDesign, designIndex: Int) -> Unit,
    val onReturn: () -> Unit
) : BaseScreen() {
    override fun show() {
        super.show()
        val root = Table()
        stage.addActor(root)
        root.setFillParent(true)
        root.add(Label("Your ship designs:", baseSkin)).pad(20f).colspan(2).row()
        for (i in playerEmpire.shipDesigns.indices) {
            val design = playerEmpire.shipDesigns[i]
            val button = if (design == null) {
                TextButton("[NEW DESIGN]", baseSkin)
            } else {
                TextButton(design.name, baseSkin).also {
                    it.addListener(object : ClickListener() {
                        override fun clicked(event: InputEvent?, x: Float, y: Float) {
                            onDesignSelected(design, i)
                        }
                    })
                }
            }
            root.add(button).growX().growY().pad(20f)
            if (i % 2 == 1)
                root.row()
        }
        root.add(
            TextButton("Back", baseSkin).also {
                it.addListener(object : ClickListener() {
                    override fun clicked(event: InputEvent?, x: Float, y: Float) {
                        onReturn()
                    }
                })
            }
        ).colspan(2).growX().growY().pad(20f).row()
        root.invalidateHierarchy()
    }
}
