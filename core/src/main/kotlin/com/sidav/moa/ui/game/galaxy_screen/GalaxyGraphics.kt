package com.sidav.moa.ui.game.galaxy_screen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture

internal object GalaxyGraphics {
    private const val STAR_SIZE = 35f.toInt()

    val redStarTxtr by lazy { createStarTexture(STAR_SIZE / 3, Color.RED) }
    val greenStarTxtr by lazy { createStarTexture(STAR_SIZE / 3, Color.GREEN) }
    val yellowStarTxtr by lazy { createStarTexture(STAR_SIZE / 3, Color.YELLOW) }
    val blueStarTxtr by lazy { createStarTexture(STAR_SIZE / 3, Color.BLUE) }
    val whiteStarTxtr by lazy { createStarTexture(STAR_SIZE / 3, Color.LIGHT_GRAY) }
    val neutrStarTxtr by lazy { createStarTexture(STAR_SIZE / 3, Color.PURPLE) }


    fun createStarTexture(radius: Int, color: Color): Texture {
        val pixmap = Pixmap(STAR_SIZE, STAR_SIZE, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)
        pixmap.fillCircle(radius, radius, radius)
        val texture = Texture(pixmap)
        pixmap.dispose()
        return texture
    }
}
