package com.sidav.moa.game.space

import com.sidav.moa.game.colony.Colony
import com.sidav.moa.game.empire.Empire

enum class PlanetType {
    TERRAN,
    JUNGLE,
    OCEAN,
    ARID,
    STEPPE,
    DESERT,
    MINIMAL,
    // Hostile
    BARREN,
    TUNDRA,
    DEAD,
    INFERNO,
    TOXIC,
    RADIATED,
    //
    NOT_INHABITABLE;

    // Those are needed by map generator
    fun betterOrEqualTo(p2: PlanetType): Boolean = ordinal <= p2.ordinal
    fun worseOrEqualTo(p2: PlanetType): Boolean = ordinal >= p2.ordinal
    fun howBetterIsFrom(p2: PlanetType): Int = p2.ordinal - ordinal
}
enum class PlanetGrowth {
    HOSTILE,
    NORMAL,
    FERTILE,
    GAIA
}

enum class PlanetSpecial {
    NONE,
    // Richness modifier
    ULTRARICH,
    RICH,
    POOR,
    ULTRAPOOR,
    // Artifacts
    ARTIFACTS,
    ORION_TECH
}

enum class PlanetAsteroids {
    NONE,
    SOME,
    MANY,
}
