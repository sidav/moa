package com.sidav.moa.util

import kotlin.random.Random
import kotlin.random.nextInt

object RandomUtil {
    fun <T> weightedRandom(items: List<Pair<T, Int>>): T {
        val totalWeight = items.sumOf { it.second }
        var random = Random.nextInt(totalWeight+1)
        for ((item, weight) in items) {
            random -= weight
            if (random <= 0) return item
        }
        return items.last().first
    }
}
