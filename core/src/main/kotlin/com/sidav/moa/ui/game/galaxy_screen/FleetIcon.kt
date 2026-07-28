package com.sidav.moa.ui.game.galaxy_screen

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sidav.moa.game.ship.Fleet

class FleetIcon(val fleet: Fleet) : Image(GalaxyGraphics.fleetTexture(fleet.owner.color)) {
}
