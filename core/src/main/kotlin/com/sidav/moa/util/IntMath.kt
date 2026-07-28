package com.sidav.moa.util

fun Int.roundToNearestFive(): Int = ((this + 2) / 5) * 5
fun Int.roundToNearestFiveF(): Float = roundToNearestFive().toFloat()

fun Int.roundToNearest(step: Int): Int = ((this + step / 2) / step) * step
fun Int.roundToNearestF(step: Int): Float = roundToNearest(step).toFloat()
