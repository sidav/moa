package com.sidav.moa.ui.game.galaxy_screen

import com.badlogic.gdx.math.Vector2

object FleetRelativePositions {
    private const val FLEET_ORBIT_RADIUS = 20f

    val slotOffsets = listOf(
        Vector2(FLEET_ORBIT_RADIUS, 0f),
        Vector2(-FLEET_ORBIT_RADIUS, 0f),
        Vector2(0f, FLEET_ORBIT_RADIUS),
        Vector2(0f, -FLEET_ORBIT_RADIUS),
        Vector2(FLEET_ORBIT_RADIUS * 0.7f, FLEET_ORBIT_RADIUS * 0.7f),
        Vector2(-FLEET_ORBIT_RADIUS * 0.7f, FLEET_ORBIT_RADIUS * 0.7f),
        Vector2(FLEET_ORBIT_RADIUS * 0.7f, -FLEET_ORBIT_RADIUS * 0.7f),
        Vector2(-FLEET_ORBIT_RADIUS * 0.7f, -FLEET_ORBIT_RADIUS * 0.7f)
    )
}
