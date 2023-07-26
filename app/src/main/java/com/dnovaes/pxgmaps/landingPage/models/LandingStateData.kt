package com.dnovaes.pxgmaps.landingPage.models

import com.dnovaes.pxgmaps.ui.StateDataInterface

data class LandingStateData(
    val menuItems: List<MenuItem> = emptyList()
): StateDataInterface
