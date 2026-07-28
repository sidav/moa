package com.sidav.moa.game.tech

import com.sidav.moa.game.tech.items.BaseTechItem

class OngoingResearch {
    var rpSpent = 0f
    var currentResearchedTech: BaseTechItem? = null
    override fun toString(): String {
       return "${currentResearchedTech?.name}: $rpSpent"
    }
}
