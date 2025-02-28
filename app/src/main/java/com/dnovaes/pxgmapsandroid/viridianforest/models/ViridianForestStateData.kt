package com.dnovaes.pxgmapsandroid.viridianforest.models

import com.dnovaes.pxgmapsandroid.landingPage.ui.models.MenuItem
import com.dnovaes.pxgmapsandroid.ui.StateDataInterface

data class ViridianForestStateData(
    val menuItems: List<List<MenuItem>> = emptyList()
): StateDataInterface
