package com.dnovaes.pxgmaps.mapsList.models

import com.dnovaes.pxgmaps.ui.StateDataInterface

data class MapStateData(
    val mapItems: List<String> = emptyList()
): StateDataInterface
