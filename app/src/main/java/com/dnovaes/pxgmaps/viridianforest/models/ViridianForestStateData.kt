package com.dnovaes.pxgmaps.viridianforest.models

import com.dnovaes.pxgmaps.landingPage.ui.models.MenuItem
import com.dnovaes.pxgmaps.ui.StateDataInterface

data class ViridianForestStateData(
    val menuItems: List<List<MenuItem>> = emptyList()
): StateDataInterface
