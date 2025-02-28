package com.dnovaes.pxgmapsandroid.landingPage.ui.models

import com.dnovaes.pxgmapsandroid.ui.StateDataInterface

data class LandingStateData(
    val menuItems: List<MenuItem> = emptyList()
): StateDataInterface
