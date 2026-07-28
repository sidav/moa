package com.sidav.moa.ui.game.galaxy_screen

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Slider
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Value
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.VisUI
import com.sidav.moa.game.ship.Fleet
import com.sidav.moa.game.ship.ShipDesign
import com.sidav.moa.game.space.Galaxy
import com.sidav.moa.game.space.Star
import com.sidav.moa.ui.util.StagePercentWidth
import com.sidav.moa.util.ceilInt
import com.sidav.moa.util.truncate

class FleetInfoPanel(private val galaxy: Galaxy, private val onFleetSent: () -> Unit) : Table() {
    var currentFleet: Fleet? = null
        private set
    var targetStar: Star? = null
        private set

    private val sliderColumn = Table()
    private val rightColumn = Table()
    private val splitAmounts = mutableMapOf<ShipDesign, Int>()
    private val sliderRefs = mutableMapOf<ShipDesign, Slider>()
    private lateinit var sendButton: TextButton
    private lateinit var colonizeButton: TextButton
    private lateinit var allButton: TextButton
    private var targetInfoLabel = Label("", VisUI.getSkin()).also { it.setAlignment(Align.center) }

    init {
        background = VisUI.getSkin().getDrawable("window-bg")
        isVisible = false
        touchable = Touchable.enabled
        addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Empty, just to consume clicks so they don't fall through to the galaxy map
            }
        })

        add(targetInfoLabel).colspan(2).growX().row()

        add(sliderColumn).width(StagePercentWidth(0.7f)).top()
        add(rightColumn).width(StagePercentWidth(0.3f)).top()
    }

    fun show(fleet: Fleet) {
        currentFleet = fleet
        sliderColumn.clear()
        rightColumn.clear()
        splitAmounts.clear()
        sliderRefs.clear()

        val rowHeight = 50f

        sendButton = TextButton("Send Fleet", VisUI.getSkin())

        for ((design, count) in fleet.ships) {
            splitAmounts[design] = 0

            val remainingLabel = Label("", VisUI.getSkin())
            val sendingLabel = Label("", VisUI.getSkin())

            val slider = Slider(0f, count.toFloat(), 1f, false, VisUI.getSkin())

            slider.addListener(object : ChangeListener() {
                override fun changed(event: ChangeEvent?, actor: Actor?) {
                    val sending = slider.value.toInt()
                    splitAmounts[design] = sending
                    remainingLabel.setText((count - sending).toString())
                    sendingLabel.setText(sending.toString())
                    updateSendButtonState()
                }
            })
            slider.value = slider.maxValue

            sliderRefs[design] = slider

            sliderColumn.add(Label(design.name.truncate(7), VisUI.getSkin())).maxWidth(60f).height(rowHeight)
                .padLeft(5f)
            sliderColumn.add(remainingLabel).width(20f).padLeft(3f)
            sliderColumn.add(slider).growX().height(rowHeight).padLeft(5f).padRight(5f)
            sliderColumn.add(sendingLabel).width(20f).padRight(3f).row()
        }

        allButton = TextButton("None/All", VisUI.getSkin())
        allButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                val allAtMaximum = sliderRefs.values.all { it.value >= it.maxValue }
                for (slider in sliderRefs.values) {
                    slider.value = if (allAtMaximum) slider.minValue else slider.maxValue
                }
                // ChangeListener fires automatically from setting .value, updating labels/state
            }
        })
        rightColumn.add(allButton).width(Value.percentWidth(0.7f, rightColumn)).height(50f).pad(5f).row()

        colonizeButton = TextButton("Colonize", VisUI.getSkin())
        colonizeButton.isDisabled = true
        colonizeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // TODO: does nothing yet
            }
        })
        rightColumn.add(colonizeButton).width(Value.percentWidth(0.7f, rightColumn)).height(50f).pad(5f).row()

        sendButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                val fleet = currentFleet ?: return
                val star = targetStar ?: return
                val amounts = splitAmounts.filterValues { it > 0 }
                galaxy.splitAndSendFleet(fleet, amounts, star)
                hide()
                onFleetSent()
            }
        })
        rightColumn.add(sendButton).width(Value.percentWidth(0.7f, rightColumn)).height(50f).pad(5f).row()

        fleet.targetStar?.let { setTargetStar(it) }
        updateSendButtonState()
        isVisible = true
    }

    fun hide() {
        currentFleet = null
        targetStar = null
        isVisible = false
    }

    fun setTargetStar(star: Star) {
        targetStar = star
        updateTargetInfoLabel()
        updateSendButtonState()
    }

    private fun updateTargetInfoLabel() {
        val fleet = currentFleet
        val star = targetStar
        if (fleet == null || star == null) {
            targetInfoLabel.setText("Tap a star to set destination")
            return
        }
        val distance = fleet.position.dst(star.position).ceilInt() // TODO: call from galaxy?
        val canReach = true // TODO: stub, always true for now
        targetInfoLabel.setText(
            if (canReach) "Target: ${star.name} Distance: $distance ETA: ${galaxy.fleetETAToStar(fleet, star)}Y"
            else "Target: ${star.name}\nUnreachable"
        )
    }

    private fun updateSendButtonState() {
        val hasShipsSelected = splitAmounts.values.any { it > 0 }
        val hasTarget = targetStar != null
        sendButton.isDisabled = !(hasShipsSelected && hasTarget)
    }
}
