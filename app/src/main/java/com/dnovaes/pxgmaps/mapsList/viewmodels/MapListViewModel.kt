package com.dnovaes.pxgmaps.mapsList.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.pxgmaps.mapsList.models.MapState
import com.dnovaes.pxgmaps.ui.models.StateStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapListViewModel: ViewModel() {

    private var localState = MapState()
    val mapState: MutableState<MapState> = mutableStateOf(localState)

    init {
        loadMaps()
    }

    fun loadMaps() {
        localState = localState
            .asLoadingMaps()
            .withState(StateStatus.PROCESSING)
        mapState.value = localState

        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)

            val mapData = localState.data
            val currentMapItems = listOf("A", "B", "C")

            localState = localState
                .asLoadingMaps()
                .withState(StateStatus.DONE)
                .withData(mapData.copy(mapItems = currentMapItems.toList()))
            mapState.value = localState
        }
    }
}
