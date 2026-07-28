package com.sidav.moa.ui.game.ship_design

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.sidav.moa.game.empire.Empire
import com.sidav.moa.game.ship.ShipDesign
import com.sidav.moa.game.ship.ShipDesignCalculator
import com.sidav.moa.game.ship.ShipSize
import com.sidav.moa.game.tech.items.ship_parts.*
import com.sidav.moa.game.tech.items.ship_parts_tech.BaseShipSpecialGivingTech
import com.sidav.moa.game.tech.items.ship_parts_tech.BaseShipWeaponGivingTech
import com.sidav.moa.ui.BaseScreen
import kotlin.math.roundToInt

class EditDesignScreen(
    val playerEmpire: Empire,
    originalDesign: ShipDesign,
    val onReturn: () -> Unit,
    val onSave: (ShipDesign) -> Unit
) : BaseScreen() {

    private val draftDesign = originalDesign.deepCopy()
    private val contentItemHeight = 40f

    // Top
    private var saveButton =
        TextButton("Save", baseSkin).also {
            it.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    if (it.isDisabled)
                        showOkDialog("Your ship design has problems.")
                    else
                        onSave(draftDesign)
                }
            })
        }

    // Bottom labels
    private lateinit var hullSpaceLabel: Label
    private lateinit var costLabel: Label
    private var powerLabel = Label("UNSET", baseSkin)
    private var enginesAmountLabel = Label("UNSET", baseSkin)

    override fun show() {
        super.show()

        val root = Table()
        root.setFillParent(true)
        stage.addActor(root)

        val header = Table()
        header.add(TextButton("Back", baseSkin).also {
            it.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    onReturn()
                }
            })
        }).colspan(1).height(contentItemHeight).growX().pad(10f)
        header.add(Label("Ship Designer", baseSkin)).colspan(2).growX().pad(10f)
        header.add(saveButton).colspan(1).height(contentItemHeight).growX().pad(10f)

        val footer = Table()
        hullSpaceLabel = Label("UNSET", baseSkin)
        footer.add(hullSpaceLabel).pad(10f)
        costLabel = Label("UNSET", baseSkin)
        footer.add(costLabel).pad(10f).row()
        footer.add(enginesAmountLabel).pad(10f)
        footer.add(powerLabel).pad(10f).row()

        // Scrollable content
        val content = buildContent(draftDesign, playerEmpire)

        val scrollPane = ScrollPane(content, baseSkin)
        scrollPane.setScrollingDisabled(true, false)
        scrollPane.setFadeScrollBars(false)

        root.add(header).growX().top().row()
        root.add(scrollPane).grow().row()
        root.add(footer).growX().bottom().row()

        onDesignChanged()
    }

    private fun buildContent(design: ShipDesign, playerEmpire: Empire): Table {
        val content = Table()
        content.top()

        content.add(Label("Name", baseSkin)).left().row()
        val nameField = TextField(design.name, baseSkin)
        nameField.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                design.name = nameField.text
            }
        })
        content.add(nameField).growX().height(contentItemHeight).padBottom(15f).row()

        content.add(Label("Hull Size", baseSkin)).left().row()
        content.add(buildHullSizeSelector(design)).growX().padBottom(15f).row()

        content.add(Label("Computer", baseSkin)).left().row()
        content.add(
            buildShipPartSelector(
                current = design.computer,
                techOptions = playerEmpire.techTree.ownedPartsOfType<ShipComputer>(),
                allowNone = true
            ) { design.computer = it; onDesignChanged() }
        ).growX().height(contentItemHeight).padBottom(15f).row()

        content.add(Label("Shield", baseSkin)).left().row()
        content.add(
            buildShipPartSelector(
                current = design.shield,
                techOptions = playerEmpire.techTree.ownedPartsOfType<ShipShield>(),
                allowNone = true
            ) { design.shield = it; onDesignChanged() }
        ).growX().height(contentItemHeight).padBottom(15f).row()

        content.add(Label("ECM", baseSkin)).left().row()
        content.add(
            buildShipPartSelector(
                current = design.ecm,
                techOptions = playerEmpire.techTree.ownedPartsOfType<ShipEcm>(),
                allowNone = true
            ) { design.ecm = it; onDesignChanged() }
        ).growX().height(contentItemHeight).padBottom(15f).row()

        content.add(Label("Armor", baseSkin)).left().row()
        content.add(
            buildShipPartSelector(
                current = design.armor,
                techOptions = playerEmpire.techTree.ownedPartsOfType<ShipArmor>(),
                allowNone = false
            ) { design.armor = it!!; onDesignChanged() }
        ).growX().height(contentItemHeight).padBottom(15f).row()

        content.add(Label("Engine", baseSkin)).left().row()
        content.add(
            buildShipPartSelector(
                current = design.engine,
                techOptions = playerEmpire.techTree.ownedPartsOfType<ShipEngines>(),
                allowNone = false
            ) {
                design.engine = it!!
                design.maneuverabilityClass = design.maneuverabilityClass.coerceIn(1, it.maxManeuverability)
                refreshManeuverabilityControls()
                onDesignChanged()
            }
        ).growX().height(contentItemHeight).padBottom(15f).row()

        content.add(buildManeuverabilitySelector())
            .growX().height(contentItemHeight).padBottom(15f).row()


        content.add(Label("Weapons", baseSkin)).left().padTop(10f).row()
        for (slot in design.weapons.indices) {
            content.add(buildWeaponSlotSelector(design, slot, playerEmpire))
                .growX().height(contentItemHeight).padBottom(10f).row()
        }

        content.add(Label("Special", baseSkin)).left().padTop(10f).row()
        for (slot in design.specials.indices) {
            content.add(buildSpecialSlotSelector(design, slot, playerEmpire))
                .growX().height(contentItemHeight).padBottom(10f).row()
        }

        return content
    }

    private fun buildHullSizeSelector(design: ShipDesign): Table {
        val row = Table()
        val buttonGroup = ButtonGroup<TextButton>()
        buttonGroup.setMinCheckCount(1)
        buttonGroup.setMaxCheckCount(1)

        for (size in ShipSize.entries) {
            val button = TextButton(size.label, baseSkin)
            button.isChecked = design.hullSize == size // This won't work, dunno why, maybe because of skin
            if (button.isChecked)
                button.color = Color.GREEN
            buttonGroup.add(button)

            button.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    design.hullSize = size
                    buttonGroup.buttons.forEach { it.color = Color.WHITE }
                    button.color = Color.GREEN
                    onDesignChanged()
                }
            })
            row.add(button).growX().height(contentItemHeight)
        }
        return row
    }

    private fun <T : BaseShipPart> buildShipPartSelector(
        current: BaseShipPart?,
        techOptions: List<T>,
        allowNone: Boolean,
        onSelected: (T?) -> Unit
    ): SelectBox<String> {
        val selectBox = SelectBox<String>(baseSkin)
        val labels = mutableListOf<String>()
        if (allowNone) labels.add("None")
        labels.addAll(techOptions.map { it.label })
        selectBox.setItems(*labels.toTypedArray())
        selectBox.selected = current?.label ?: "None"

        selectBox.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                val selectedName = selectBox.selected
                val chosen = if (selectedName == "None") null else techOptions.find { it.label == selectedName }
                onSelected(chosen)
            }
        })
        return selectBox
    }

    private lateinit var mnvrCountLabel: Label
    private lateinit var mnvrMinusButton: TextButton
    private lateinit var mnvrPlusButton: TextButton

    private fun buildManeuverabilitySelector(): Table {
        val row = Table()

        mnvrCountLabel = Label(draftDesign.maneuverabilityClass.toString(), baseSkin)
        mnvrCountLabel.setAlignment(Align.center)
        mnvrMinusButton = TextButton("-", baseSkin)
        mnvrPlusButton = TextButton("+", baseSkin)

        mnvrMinusButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                setManeuverability(draftDesign.maneuverabilityClass - 1)
            }
        })

        mnvrPlusButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                setManeuverability(draftDesign.maneuverabilityClass + 1)
            }
        })

        refreshManeuverabilityControls()

        row.add(Label("Maneuverability:", baseSkin)).padRight(10f)
        row.add(mnvrMinusButton).width(30f).height(contentItemHeight)
        row.add(mnvrCountLabel).width(40f)
        row.add(mnvrPlusButton).width(30f).height(contentItemHeight)

        return row
    }

    private fun setManeuverability(value: Int) {
        draftDesign.maneuverabilityClass = value.coerceIn(1, draftDesign.engine.maxManeuverability)
        refreshManeuverabilityControls()
        onDesignChanged()
    }

    private fun refreshManeuverabilityControls() {
        mnvrCountLabel.setText(draftDesign.maneuverabilityClass.toString())
        mnvrMinusButton.isDisabled = draftDesign.maneuverabilityClass <= 1
        mnvrPlusButton.isDisabled = draftDesign.maneuverabilityClass >= draftDesign.engine.maxManeuverability
    }

    private fun buildWeaponSlotSelector(design: ShipDesign, slot: Int, playerEmpire: Empire): Table {
        val row = Table()

        val weaponOptions =
            playerEmpire.techTree.ownedTechOfType<BaseShipWeaponGivingTech<BaseShipWeapon>>().flatMap { it.givesItems }
        val labels = listOf("Empty") + weaponOptions.map { it.label }

        val selectBox = SelectBox<String>(baseSkin)
        selectBox.setItems(*labels.toTypedArray())

        val currentWeapon = design.weapons[slot]
        selectBox.selected = currentWeapon?.item?.label ?: "Empty"

        val countLabel = Label((currentWeapon?.count ?: 1).toString(), baseSkin)
        countLabel.setAlignment(Align.center)
        val minusButton = TextButton("-", baseSkin)
        val plusButton = TextButton("+", baseSkin)

        fun currentCount(): Int = countLabel.text.toString().toIntOrNull() ?: 1

        fun updateCountControlsEnabled() {
            val isEmpty = selectBox.selected == "Empty"
            minusButton.isDisabled = isEmpty || currentCount() <= 1
            plusButton.isDisabled = isEmpty
            countLabel.isVisible = !isEmpty
            minusButton.isVisible = !isEmpty
            plusButton.isVisible = !isEmpty
        }

        fun onWeaponSlotUpdated() {
            val selectedName = selectBox.selected
            if (selectedName == "Empty") {
                design.weapons[slot] = null
            } else {
                val chosenTech = weaponOptions.find { it.label == selectedName } ?: return
                design.weapons[slot] = ShipDesign.WeaponSlot(chosenTech as BaseShipWeapon, currentCount())
            }
            updateCountControlsEnabled()
            onDesignChanged()
        }

        selectBox.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                if (selectBox.selected != "Empty" && currentCount() < 1) {
                    countLabel.setText("1")
                }
                onWeaponSlotUpdated()
            }
        })

        minusButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (currentCount() > 1) {
                    countLabel.setText((currentCount() - 1).toString())
                    onWeaponSlotUpdated()
                }
            }
        })

        plusButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                countLabel.setText((currentCount() + 1).toString())
                onWeaponSlotUpdated()
            }
        })

        updateCountControlsEnabled()

        row.add(Label("Slot ${slot + 1}:", baseSkin)).padRight(10f)
        row.add(selectBox).growX().height(contentItemHeight).padRight(10f)
        row.add(minusButton).width(30f).height(contentItemHeight)
        row.add(countLabel).width(40f)
        row.add(plusButton).width(30f).height(contentItemHeight)

        return row
    }

    private fun buildSpecialSlotSelector(design: ShipDesign, slot: Int, playerEmpire: Empire): Table {
        val row = Table()
        val allSpecials = playerEmpire.techTree.ownedTechOfType<BaseShipSpecialGivingTech<BaseShipSpecial>>()
            .flatMap { it.givesItems }

        row.add(Label("Special ${slot + 1}:", baseSkin)).padRight(10f)

        lateinit var selectBox: SelectBox<String>
        selectBox = buildShipPartSelector(
            current = design.specials[slot],
            techOptions = allSpecials,
            allowNone = true
        ) { chosen ->
            val isDuplicate = chosen != null && design.specials.filterIndexed { i, _ -> i != slot }.any { it == chosen }
            if (isDuplicate) {
                selectBox.selected = "None"
                design.specials[slot] = null
                showOkDialog("${chosen.label} is already installed in another slot.")
            } else {
                design.specials[slot] = chosen
            }
            onDesignChanged()
        }

        row.add(selectBox).growX().height(contentItemHeight)
        return row
    }

    private fun onDesignChanged() {
        ShipDesignCalculator.updateDesignStats(draftDesign, playerEmpire)
        hullSpaceLabel.setText("Hull space: ${draftDesign.occupiedSpace}/${draftDesign.totalSpace}")
        costLabel.setText("Cost to build: ${draftDesign.cost.roundToInt()} BC")
        enginesAmountLabel.setText("Engines required: ${draftDesign.enginesAmount}")
        powerLabel.setText("Power: ${draftDesign.enginesAmount}/${draftDesign.totalPower}")
        if (!draftDesign.isValid()) {
            hullSpaceLabel.color = Color.RED
            saveButton.isDisabled = true
        } else {
            hullSpaceLabel.color = Color.WHITE
            saveButton.isDisabled = false
        }
    }
}
