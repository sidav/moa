package com.sidav.moa.util

import kotlin.math.abs

fun Float.toFixed1(): String = "%.1f".format(this)
fun Float.floor(): Float = kotlin.math.floor(this)
fun Float.ceilInt(): Int = kotlin.math.ceil(this).toInt()
fun Float.epsilonEquals(f2: Float, epsilon: Float = 0.0005f): Boolean = abs(this - f2) <= epsilon
fun Float.epsilonLessOrEquals(other: Float, epsilon: Float = 0.0005f): Boolean = this <= other + epsilon

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

fun String.truncate(maxLength: Int): String =
    if (length <= maxLength) this else take(maxLength - 1) + "…"

fun <K> MutableMap<K, Int>.decreaseOrRemove(key: K, amount: Int) {
    val current = this[key] ?: error("Cannot decrease: key '$key' not found")
    val remaining = current - amount
    if (remaining > 0)
        this[key] = remaining
    else
        remove(key)
}
