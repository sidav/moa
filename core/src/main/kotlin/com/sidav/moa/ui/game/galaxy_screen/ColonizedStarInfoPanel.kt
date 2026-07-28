package com.sidav.moa.ui.game.galaxy_screen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Slider
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Value
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.VisUI
import com.sidav.moa.game.colony.Colony
import com.sidav.moa.game.colony.SpendingArea
import com.sidav.moa.game.colony.maxSpendingTicksF
import com.sidav.moa.game.space.Star
import com.sidav.moa.ui.util.StagePercentWidth
import kotlin.collections.iterator

class ColonizedStarInfoPanel : Table() {
    var currentStar: Star? = null
    private val sliderRefs = mutableMapOf<SpendingArea, Slider>()
    private var skipUpdateEvent = false // needed if a slider is changed by a game mechanic

    private val slidersColumn = Table()
    private val rightColumn = Table()

    init {
        background = VisUI.getSkin().getDrawable("window-bg")
        isVisible = false
        touchable = Touchable.enabled
        addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Empty
            }
        })
        add(slidersColumn).width(StagePercentWidth(0.6f)).top()
        add(rightColumn).width(StagePercentWidth(0.4f)).top()
    }

    fun show(star: Star) {
        currentStar = star
        slidersColumn.clear()
        rightColumn.clear()
        sliderRefs.clear()

        val colony = star.colony ?: error("Wrong panel shown!")


        val sliderHeight = 50f
        // LEFT SUBPANEL
        for (area in SpendingArea.entries) {
            val areaLabel = Label(area.name, VisUI.getSkin())
            val slider = Slider(0f, maxSpendingTicksF, 1f, false, VisUI.getSkin())
            slider.value = colony.budget.allocatedTicksf(area)
            if (colony.budget.getLocked(area)) {
                areaLabel.color = Color.RED
                slider.color = Color.RED
            }

            areaLabel.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    colony.budget.toggleLocked(area)
                    areaLabel.color =
                        if (colony.budget.getLocked(area)) Color.RED else Color.WHITE
                    slider.touchable =
                        if (colony.budget.getLocked(area)) Touchable.disabled else Touchable.enabled
                    slider.color = if (colony.budget.getLocked(area)) Color.RED else Color.WHITE
                }
            })

            // Shows what the slider actually does
            val sliderEffectLbl = Label(colony.budget.get(area).text(), VisUI.getSkin())

            slider.addListener(object : ChangeListener() {
                override fun changed(event: ChangeEvent?, actor: Actor?) {
                    sliderEffectLbl.setText(colony.budget.get(area).text())
                    if (skipUpdateEvent) return
                    colony.budget.setAllocSliderTicks(area, slider.value.toInt())
                    refreshSliders(colony)
                }
            })

            sliderRefs[area] = slider
            slidersColumn.add(areaLabel).maxWidth(110f).height(sliderHeight).padLeft(10f)
            slidersColumn.add(slider).growX().height(sliderHeight).padLeft(5f)
            slidersColumn.add(sliderEffectLbl).maxWidth(35f).padLeft(3f).padRight(3f).row()
        }
        // RIGHT SUBPANEL
        val starInfoLabel = Label(buildString {
            append(star.asShortString())
            append("\n")
            append(colony.asString())
        }, VisUI.getSkin())
        starInfoLabel.wrap = true
        starInfoLabel.setAlignment(Align.right)
        rightColumn.add(starInfoLabel).width(Value.percentWidth(1f, rightColumn)).pad(10f).row()

//        this.setDebug(true, true)

        isVisible = true
    }

    fun hide() {
        currentStar = null
        isVisible = false
    }

    fun refresh() {
        currentStar?.let { show(it) }
    }

    private fun refreshSliders(colony: Colony) {
        skipUpdateEvent = true
        for ((area, slider) in sliderRefs) {
            slider.value = colony.budget.allocatedTicksf(area)
        }
        skipUpdateEvent = false
    }
}
