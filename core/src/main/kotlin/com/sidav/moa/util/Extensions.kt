package com.sidav.moa.util

fun Float.toFixed1(): String = "%.1f".format(this)
fun Float.floor(): Float = kotlin.math.floor(this)
fun Float.ceilInt(): Int = kotlin.math.ceil(this).toInt()

fun Int.square(): Int = this * this
fun Int.toRoman(): String = when (this) {
    1 -> "I"
    2 -> "II"
    3 -> "III"
    4 -> "IV"
    5 -> "V"
    6 -> "VI"
    7 -> "VII"
    8 -> "VIII"
    9 -> "IX"
    10 -> "X"
    11 -> "XI"
    12 -> "XII"
    13 -> "XIII"
    14 -> "XIV"
    15 -> "XV"
    16 -> "XVI"
    else -> this.toString()
}
