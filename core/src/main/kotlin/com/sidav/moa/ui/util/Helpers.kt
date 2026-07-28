package com.sidav.moa.ui.util

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Value

class StagePercentWidth(private val pct: Float, private val fallback: Float = 360f) : Value() {
    override fun get(context: Actor?): Float =
        (context?.stage?.width ?: fallback) * pct
}
