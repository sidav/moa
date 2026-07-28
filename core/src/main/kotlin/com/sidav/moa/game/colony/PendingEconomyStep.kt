package com.sidav.moa.game.colony

// This is needed for calculating spendings' nextTurn().
// This encapsulates the current colony/planet values from the budget
// (the changes to planet/colony are applied in Colony, not in the ind/eco/etc themselves)
internal class PendingEconomyStep(
    // Before the calculation
    val popBefore: Float,
    val netBcBefore: Float,
    val reserveBefore: Float,
    val planetWasteBefore: Int,
    val wasteProducedByColony: Int
) {
    val netBcWithReserveBefore = netBcBefore + reserveBefore
    // After the calculations:
    // IND
    var newFactories = 0f
    var wasteChange = 0
    var roboticControlsBeingImproved = false
    var roboticControlsImproveFinished = false
    // ECO
    var boughtPop = 0f
    var terraformingChange = 0
    // TECH
    var newRp = 0f
    // Misc
    var unspentBc = 0f // What should be added to the reserve
}
