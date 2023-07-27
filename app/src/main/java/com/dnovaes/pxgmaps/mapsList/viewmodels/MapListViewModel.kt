package com.dnovaes.pxgmaps.mapsList.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.pxgmaps.mapsList.models.MapItem
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
        mapState.value = localState

        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)

            val mapData = localState.data
            val currentMapItems = listOf(
                MapItem(
                    title = "Outland East",
                    url = "http://pxgmaps.com.br/map?name=outlandeast_2858_2956_8",
                    coordinates = "2858,2956,8",
                ),
                MapItem(
                    title = "Mandarin",
                    url = "http://pxgmaps.com.br/map?name=mandarin_3928_6058_6",
                    coordinates = "3928,6058,6",
                ),
                MapItem(
                    title = "Pinkanisland",
                    url = "http://pxgmaps.com.br/map?name=pinkanisland_3207_5019_4",
                    coordinates = "3207,5019,4",
                ),
            )

            localState = localState
                .asLoadingMaps()
                .withState(StateStatus.DONE)
                .withData(mapData.copy(mapItems = currentMapItems.toList()))
            mapState.value = localState
        }
    }
}
