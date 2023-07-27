package com.dnovaes.pxgmaps.mapsList.models

import com.dnovaes.pxgmaps.ui.StateDataInterface

data class MapStateData(
    val mapItems: List<MapItem> = emptyList()
): StateDataInterface

data class MapItem(
    val title: String,
    val url: String,
    val coordinates: String
)
