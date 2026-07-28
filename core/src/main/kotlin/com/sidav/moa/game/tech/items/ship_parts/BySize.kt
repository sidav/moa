package com.sidav.moa.game.tech.items.ship_parts

import com.sidav.moa.game.ship.ShipSize

/**
 * Needed for ship part values if they're different for different ship sizes.
 */
class ByShipSize<T>(
    small: T,
    medium: T,
    large: T,
    huge: T
) {
    constructor(sameForAll: T) : this(sameForAll, sameForAll, sameForAll, sameForAll)

    private val values = mapOf<ShipSize, T>(
        ShipSize.SMALL to small,
        ShipSize.MEDIUM to medium,
        ShipSize.LARGE to large,
        ShipSize.HUGE to huge
    )
    operator fun get(size: ShipSize): T = values.getValue(size)
}
