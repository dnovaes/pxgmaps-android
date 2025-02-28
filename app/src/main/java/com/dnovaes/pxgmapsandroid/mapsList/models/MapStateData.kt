package com.dnovaes.pxgmapsandroid.mapsList.models

import com.dnovaes.pxgmapsandroid.ui.StateDataInterface

data class MapStateData(
    val mapItems: List<MapItem> = emptyList()
): StateDataInterface

data class MapItem(
    val title: String,
    val url: String,
    val coordinates: String
)
