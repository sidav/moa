package com.sidav.moa.ui.game.galaxy_screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Value
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.MenuItem
import com.kotcrab.vis.ui.widget.PopupMenu
import com.sidav.moa.game.GameState
import com.sidav.moa.game.space.Star
import com.sidav.moa.game.space.StarType
import ktx.app.KtxScreen
import kotlin.random.Random

class GalaxyScreen(
    private val gameState: GameState, val onEndTurnCallback: () -> Unit
) : KtxScreen {
    private val virtualW = 360f
    private val virtualH = 780f
    private val panelsH = 300f
    private val worldStage = Stage(ExtendViewport(virtualW, virtualH))

    //    private val hudStage = Stage(ScreenViewport().apply {
//        unitsPerPixel = 1f / Gdx.graphics.density
//    })
//    private val hudStage = Stage(FitViewport(900f, 1600f))
    private val hudStage = Stage(ExtendViewport(virtualW, virtualH))

    private val parsecSize = 35f // A single "cell" of the game field

    private val starInfoPanel = StarInfoPanel()
    private val colonizedStarInfoPanel = ColonizedStarInfoPanel()
    private var lastSelectedStar: Star = gameState.playerEmpire().colonies[0]

    init {
        addScrolling()
        addDeselectListener()
    }

    override fun show() {
        Gdx.input.inputProcessor =
            InputMultiplexer(hudStage, worldStage) // hudStage первым — его клики приоритетнее

        addStarsOnStage()

        buildHud()
        lastSelectedStar?.let { centerOnStar(it) }
    }

    private fun buildHud() {
        val root = Table()
        root.setFillParent(true)
        root.bottom()
        root.stack(starInfoPanel, colonizedStarInfoPanel).growX().height(panelsH)
        hudStage.addActor(root)

        val endTurnButton = TextButton("TURN ${gameState.currentTurn}\nEND TURN", VisUI.getSkin())
        endTurnButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                onEndTurnCallback()
            }
        })

        val topRight = Table()
        topRight.setFillParent(true)
        topRight.top().right()
        topRight.add(endTurnButton).width(Value.percentWidth(0.3f, topRight))
            .height(Value.percentHeight(0.1f, topRight)).pad(10f)
        hudStage.addActor(topRight)

        // Top left: ingame popup menu
        val topLeft = Table()
        topLeft.setFillParent(true)
        topLeft.top().left()

        val menuButton = TextButton("Menu", VisUI.getSkin())
        val popupMenu = buildGalaxyPopupMenu()
        menuButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                popupMenu.showMenu(hudStage, 10f, virtualH - 10f)
            }
        })

        topLeft.add(menuButton).width(Value.percentWidth(0.3f, topLeft))
            .height(Value.percentHeight(0.1f, topLeft)).pad(10f)
        hudStage.addActor(topLeft)
    }

    private fun buildGalaxyPopupMenu(): PopupMenu {
        val menu = PopupMenu()
        GalaxyMenuItem.entries.forEach { item ->
            val menuItem = MenuItem(item.label)//.apply { label.setFontScale(1.2f) }
            menuItem.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
//                    onMenuItemSelected(item)
                }
            })
            menu.addItem(menuItem)
            menu.getCell(menuItem)?.height(60f)
        }
        menu.invalidateHierarchy()
        menu.pack()
        return menu
    }

    private fun centerOnStar(star: Star) {
        val camera = worldStage.camera as OrthographicCamera
        camera.position.set(
            star.position.x * parsecSize, star.position.y * parsecSize - panelsH / 2, 0f
        )
        camera.update()
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.05f, 0.05f, 0.05f, 1f)
        worldStage.viewport.apply()
        worldStage.act(delta)
        worldStage.draw()
        hudStage.viewport.apply()
        hudStage.act(delta)
        hudStage.draw()
    }

    override fun resize(width: Int, height: Int) {
        worldStage.viewport.update(width, height, false)
        hudStage.viewport.update(width, height, true)
        starInfoPanel.invalidateHierarchy()
        colonizedStarInfoPanel.invalidateHierarchy()
    }

    override fun dispose() {
        worldStage.dispose()
        hudStage.dispose()
    }

    private fun addStarsOnStage() {
        for (star in gameState.galaxy.stars) {
            val starTexture = when (star.sType) {
                StarType.RED -> GalaxyGraphics.redStarTxtr
                StarType.GREEN -> GalaxyGraphics.greenStarTxtr
                StarType.YELLOW -> GalaxyGraphics.yellowStarTxtr
                StarType.BLUE -> GalaxyGraphics.blueStarTxtr
                StarType.WHITE -> GalaxyGraphics.whiteStarTxtr
                StarType.NEUTRON -> GalaxyGraphics.neutrStarTxtr
            }
            val icon = Image(starTexture)
            icon.setPosition(star.position.x * parsecSize, star.position.y * parsecSize)
            // Click on a star
            icon.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    centerOnStar(star)
                    lastSelectedStar = star
                    if (star.isColonized()) {
                        colonizedStarInfoPanel.show(star)
                        starInfoPanel.hide()
                    } else {
                        starInfoPanel.show(star)
                        colonizedStarInfoPanel.hide()
                    }
                }
            })
            worldStage.addActor(icon)

            if (star.isColonized()) {
                val nameLabel = Label(star.name, VisUI.getSkin())
                nameLabel.setFontScale(1f)
                nameLabel.setPosition(
                    icon.x + parsecSize / 2f - nameLabel.width / 2f, // центрируем по горизонтали относительно иконки
                    icon.y - nameLabel.height // чуть ниже иконки
                )
                worldStage.addActor(nameLabel)
            }
        }
    }

    private fun addScrolling() {
        worldStage.addListener(object : DragListener() {
            override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                val camera = worldStage.camera as OrthographicCamera
                val viewport = worldStage.viewport

                val worldDeltaX =
                    -Gdx.input.deltaX * camera.zoom * viewport.worldWidth / viewport.screenWidth
                val worldDeltaY =
                    Gdx.input.deltaY * camera.zoom * viewport.worldHeight / viewport.screenHeight

                camera.translate(worldDeltaX, worldDeltaY, 0f)
                camera.update()
            }
        })
    }

    private fun addDeselectListener() {
        worldStage.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (event?.target == worldStage.root) {
                    colonizedStarInfoPanel.hide()
                    starInfoPanel.hide()
                }
            }
        })
        centerOnStar(gameState.playerEmpire().colonies[0])
    }

    fun refreshColonyPanel() {
        colonizedStarInfoPanel.refresh()
    }



    enum class GalaxyMenuItem(val label: String) {
        SHIP_DESIGN("Design"),
        TECH_TREE("Fleet"),
        GALAXY_MAP("Map"),
        COLONIES("Races"),
        PLANETS("Planets"),
    }
}
