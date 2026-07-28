package com.sidav.moa.game.space

import com.badlogic.gdx.math.Vector2
import com.sidav.moa.game.colony.Colony
import kotlin.math.roundToInt

class Star(var name: String, var sType: StarType, val position: Vector2) {
    // Planet data
    var planetType = PlanetType.NOT_INHABITABLE
    var planetSpecial = PlanetSpecial.NONE
    var planetAsteroids = PlanetAsteroids.NONE
    var planetGrowth = PlanetGrowth.NORMAL
    private var pBaseSize: Float = 10f
    fun setPlanetBaseSize(s: Float) {pBaseSize = s}
    var pTerraformLevel: Int = 0
    var pWaste: Int = 0
    fun maxWaste() = (pCurrentSizeNoWaste() * 0.9f).roundToInt()
    fun pCurrentSizeNoWaste() = pBaseSize + pTerraformLevel
    fun pCurrentSizeAfterWaste() = pCurrentSizeNoWaste() - pWaste
    fun pMaxWaste() = pCurrentSizeNoWaste() * 0.9f

    // Colony
    var colony: Colony? = null

    fun asString(): String {
        var str = "$name: ${sType.name} star"
        if (hasInhabitablePlanet()) {
            str += "\n${planetType.name} planet" +
                "\nMAX ${pCurrentSizeNoWaste().toInt()} POP"
            if (planetAsteroids != PlanetAsteroids.NONE)
                str += "\n${planetAsteroids.name} asteroids"
            if (planetGrowth != PlanetGrowth.NORMAL)
                str += "\n${planetGrowth.name}"
            if (planetSpecial != PlanetSpecial.NONE)
                str += "\n${planetSpecial.name}"
        } else {
            str += "\nno planets"
        }
        return str
    }
    fun asShortString(): String {
        var str = "$name: ${sType.name} star"
        if (hasInhabitablePlanet()) {
            str += "\n${planetType.name} planet"
            if (planetAsteroids != PlanetAsteroids.NONE)
                str += "\n${planetAsteroids.name} asteroids"
            if (planetGrowth != PlanetGrowth.NORMAL)
                str += "\n${planetGrowth.name}"
            if (planetSpecial != PlanetSpecial.NONE)
                str += "\n${planetSpecial.name}"
        } else {
            str += "\nno planets"
        }
        return str
    }

    fun isColonized(): Boolean = colony != null
    fun hasInhabitablePlanet(): Boolean = planetType != PlanetType.NOT_INHABITABLE
}

enum class StarType(val probabilityWght: Int, val planetTypeWeights: List<Pair<PlanetType, Int>>) {
    RED(
        6, listOf(
            PlanetType.NOT_INHABITABLE to 2,
            PlanetType.RADIATED to 1,
            PlanetType.TOXIC to 1,
            PlanetType.INFERNO to 1,
            PlanetType.DEAD to 1,
            PlanetType.TUNDRA to 1,
            PlanetType.BARREN to 1,
            PlanetType.MINIMAL to 1,
            PlanetType.DESERT to 2,
            PlanetType.STEPPE to 2,
            PlanetType.ARID to 3,
            PlanetType.OCEAN to 2,
            PlanetType.JUNGLE to 2,
            PlanetType.TERRAN to 1
        )
    ),
    GREEN(
        5, listOf(
            PlanetType.NOT_INHABITABLE to 2,
            PlanetType.RADIATED to 1,
            PlanetType.TOXIC to 1,
            PlanetType.INFERNO to 1,
            PlanetType.DEAD to 1,
            PlanetType.TUNDRA to 1,
            PlanetType.BARREN to 1,
            PlanetType.MINIMAL to 1,
            PlanetType.DESERT to 2,
            PlanetType.STEPPE to 2,
            PlanetType.ARID to 3,
            PlanetType.OCEAN to 2,
            PlanetType.JUNGLE to 2,
            PlanetType.TERRAN to 1
        )
    ),
    YELLOW(
        3, listOf(
            PlanetType.NOT_INHABITABLE to 2,
            PlanetType.RADIATED to 1,
            PlanetType.TOXIC to 1,
            PlanetType.INFERNO to 1,
            PlanetType.DEAD to 1,
            PlanetType.TUNDRA to 1,
            PlanetType.BARREN to 1,
            PlanetType.MINIMAL to 1,
            PlanetType.DESERT to 2,
            PlanetType.STEPPE to 2,
            PlanetType.ARID to 3,
            PlanetType.OCEAN to 2,
            PlanetType.JUNGLE to 2,
            PlanetType.TERRAN to 1
        )
    ),
    BLUE(
        3, listOf(
            PlanetType.NOT_INHABITABLE to 2,
            PlanetType.RADIATED to 1,
            PlanetType.TOXIC to 1,
            PlanetType.INFERNO to 1,
            PlanetType.DEAD to 1,
            PlanetType.TUNDRA to 1,
            PlanetType.BARREN to 1,
            PlanetType.MINIMAL to 1,
            PlanetType.DESERT to 2,
            PlanetType.STEPPE to 2,
            PlanetType.ARID to 3,
            PlanetType.OCEAN to 2,
            PlanetType.JUNGLE to 2,
            PlanetType.TERRAN to 1
        )
    ),
    WHITE(
        2, listOf(
            PlanetType.NOT_INHABITABLE to 2,
            PlanetType.RADIATED to 1,
            PlanetType.TOXIC to 1,
            PlanetType.INFERNO to 1,
            PlanetType.DEAD to 1,
            PlanetType.TUNDRA to 1,
            PlanetType.BARREN to 1,
            PlanetType.MINIMAL to 1,
            PlanetType.DESERT to 2,
            PlanetType.STEPPE to 2,
            PlanetType.ARID to 3,
            PlanetType.OCEAN to 2,
            PlanetType.JUNGLE to 2,
            PlanetType.TERRAN to 1
        )
    ),
    NEUTRON(
        1, listOf(
            PlanetType.NOT_INHABITABLE to 5,
            PlanetType.RADIATED to 1,
            PlanetType.TOXIC to 1,
            PlanetType.INFERNO to 1,
            PlanetType.DEAD to 1,
            PlanetType.TUNDRA to 1,
            PlanetType.BARREN to 1,
            PlanetType.MINIMAL to 1,
//            PlanetType.RADIATED to 1,
//            PlanetType.TOXIC to 1,
//            PlanetType.INFERNO to 1,
//            PlanetType.TUNDRA to 1,
//            PlanetType.BARREN to 1,
//            PlanetType.MINIMAL to 1,
//            PlanetType.DESERT to 1,
//            PlanetType.STEPPE to 1,
//            PlanetType.ARID to 1,
//            PlanetType.OCEAN to 1,
//            PlanetType.JUNGLE to 1,
//            PlanetType.TERRAN to 1
        )
    )
}

// StarNames.kt
val STAR_NAMES = listOf(
    "Gienah",
    "Vox",
    "Esper",
    "Collassa",
    "Berel",
    "Capella",
    "Celtsi",
    "Alcor",
    "Gorra",
    "Klystron",
    "Selia",
    "Hyboria",
    "Beta Ceti",
    "Yarrow",
    "Vega",
    "Gion",
    "Toranor",
    "Neptunus",
    "Reticuli",
    "Obaca",
    "Denubius",
    "Ryoun",
    "Xengara",
    "Bootis",
    "Morrig",
    "Kakata",
    "Anraq",
    "Mu Delphi",
    "Keeta",
    "Escalon",
    "Crius",
    "Centauri",
    "Maretta",
    "Rhilus",
    "Ukko",
    "Xudax",
    "Nyarl",
    "Talas",
    "Crypto",
    "Kailis",
    "Guradas",
    "Tauri",
    "Incedius",
    "Regulus",
    "Drakka",
    "Rana",
    "Whynil",
    "Proxima",
    "Artemis",
    "Dolz",
    "Willow",
    "Helos",
    "Zoctan",
    "Darrian",
    "Rigel",
    "Paladia",
    "Uxmai",
    "Xendalla",
    "Exis",
    "Pollus",
    "Kronos",
    "Quayal",
    "Tyr",
    "Simius",
    "Ajax",
    "Herculis",
    "Phantos",
    "Tao",
    "Paranar",
    "Laan",
    "Arietis",
    "Trax",
    "Romulas",
    "Antares",
    "Rayden",
    "Aquilae",
    "Vulcan",
    "Moro",
    "Thrax",
    "Lyae",
    "Nitzer",
    "Spica",
    "Omicron",
    "Jinga",
    "Rha",
    "Kulthos",
    "Cygni",
    "Galos",
    "Proteus",
    "Volantis",
    "Endoria",
    "Primodius",
    "Imra",
    "Zhardan",
    "Tau Cygni",
    "Seidon",
    "Mobas",
    "Aurora",
    "Stalaz",
    "Hyades",
    "Maalor",
    "Argus",
    "Phyco",
    "Dunatis",
    "Draconis",
    "Rotan",
    "Misha",
    "Iranha",
    "Nordia",
    "Firma"
)

