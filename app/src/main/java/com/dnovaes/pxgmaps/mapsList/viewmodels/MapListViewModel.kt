package com.dnovaes.pxgmaps.mapsList.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dnovaes.pxgmaps.mapsList.models.MapState

class MapListViewModel: ViewModel() {

    private var localState = MapState()
    val mapState: MutableState<MapState> = mutableStateOf(localState)

    init {
        loadMaps()
    }

    fun loadMaps() {
        val mapData = localState.data
        val currentMapItems = listOf("A", "B", "C")

        localState = localState
            .asLoadingMaps()
            .withData(mapData.copy(mapItems = currentMapItems.toList()))
        mapState.value = localState
    }
}
