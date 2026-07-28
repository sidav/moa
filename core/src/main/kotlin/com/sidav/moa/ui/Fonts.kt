package com.sidav.moa.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

object Fonts {
    lateinit var flexi32: BitmapFont
        private set

    fun load() {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("ttf/Flexi_IBM_VGA_False.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter().apply {
            size = 32
        }
        flexi32 = generator.generateFont(parameter)
        generator.dispose()
    }

    fun dispose() {
        flexi32.dispose()
    }
}

