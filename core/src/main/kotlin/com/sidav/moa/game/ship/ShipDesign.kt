package com.sidav.moa.game.ship

import com.sidav.moa.game.tech.items.ship_parts.*

class ShipDesign(
    var name: String,
    var hullSize: ShipSize,
    var computer: ShipComputer?,
    var shield: ShipShield?,
    var ecm: ShipEcm?,
    var armor: ShipArmor,
    var engine: ShipEngines,
    var maneuverabilityClass: Int,
    val weapons: Array<WeaponSlot?> = arrayOfNulls(4),
    val specials: Array<BaseShipSpecial?> = arrayOfNulls(3)
    // TODO: 3 special items
) {
    class WeaponSlot(var item: BaseShipWeapon, var count: Int)

    var totalSpace = 0
        internal set
    var occupiedSpace = 0
        internal set
    var cost = 0f
        internal set
    var totalPower = 0
    var consumedPower = 0
        private set
    var enginesAmount = 1 // This is not in constructor, because it's set automatically
        private set

    fun isValid(): Boolean {
        return totalSpace >= occupiedSpace && totalPower >= consumedPower
    }

    fun deepCopy(): ShipDesign {
        val copy = ShipDesign(
            name = name,
            hullSize = hullSize,
            computer = computer,
            shield = shield,
            ecm = ecm,
            armor = armor,
            engine = engine,
            maneuverabilityClass = maneuverabilityClass,
            weapons = Array(weapons.size) { i ->
                if (weapons[i] == null)
                    null
                else WeaponSlot(
                    weapons[i]!!.item,
                    weapons[i]!!.count
                )
            },
            specials = Array(specials.size) { i ->
                if (specials[i] == null)
                    null
                else specials[i]
            }
        )
        copy.totalSpace = totalSpace
        copy.occupiedSpace = occupiedSpace
        copy.cost = cost
        return copy
    }

    fun powerNeededForManeuverability() : Int {
        val basePwrReq = when(hullSize) {
            ShipSize.SMALL -> 2
            ShipSize.MEDIUM -> 15
            ShipSize.LARGE -> 100
            ShipSize.HUGE -> 700
        }
        return basePwrReq * maneuverabilityClass
    }

    private fun recalcPowerConsumption() {
        var tpc = 0
        tpc += computer?.powerConsumption[hullSize] ?: 0
        tpc += shield?.powerConsumption[hullSize] ?: 0
        tpc += ecm?.powerConsumption[hullSize] ?: 0
        for (weap in weapons) {
            if (weap == null) continue
            tpc += weap.count * weap.item.powerConsumption[hullSize]
        }
        for (spc in specials) {
            if (spc == null) continue
            tpc += spc.powerConsumption[hullSize]
        }
        tpc += powerNeededForManeuverability()
        consumedPower = tpc
    }

    fun recalcPowerAndSetEnginesAmount() {
        recalcPowerConsumption()
        val perEngine = engine.powerOutput
        enginesAmount = (consumedPower + perEngine - 1) / perEngine
        totalPower = enginesAmount * perEngine
    }
}
