package com.sidav.moa.ui.game.galaxy_screen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture

internal object GalaxyGraphics {
    private const val STAR_SIZE = 35f.toInt()
    private const val FLEET_SIZE = 25

    val redStarTxtr by lazy { createStarTexture(STAR_SIZE / 3, Color.RED) }
    val greenStarTxtr by lazy { createStarTexture(STAR_SIZE / 3, Color.GREEN) }
    val yellowStarTxtr by lazy { createStarTexture(STAR_SIZE / 3, Color.YELLOW) }
    val blueStarTxtr by lazy { createStarTexture(STAR_SIZE / 3, Color.BLUE) }
    val whiteStarTxtr by lazy { createStarTexture(STAR_SIZE / 3, Color.LIGHT_GRAY) }
    val neutrStarTxtr by lazy { createStarTexture(STAR_SIZE / 3, Color.PURPLE) }

    private val fleetTexturesByColor = mutableMapOf<Color, Texture>()

    fun createStarTexture(radius: Int, color: Color): Texture {
        val pixmap = Pixmap(STAR_SIZE, STAR_SIZE, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)
        pixmap.fillCircle(radius, radius, radius)
        val texture = Texture(pixmap)
        pixmap.dispose()
        return texture
    }

    fun fleetTexture(color: Color): Texture {
        return fleetTexturesByColor.getOrPut(color) { createTriangleTexture(FLEET_SIZE, color) }
    }

    private fun createTriangleTexture(size: Int, color: Color): Texture {
        val height = size/2
        val pixmap = Pixmap(size, size, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)
        pixmap.fillTriangle(
            size / 3, 0,
            0, height - 1,
            size - 1, height - 1
        )
        val texture = Texture(pixmap)
        pixmap.dispose()
        return texture
    }
}
