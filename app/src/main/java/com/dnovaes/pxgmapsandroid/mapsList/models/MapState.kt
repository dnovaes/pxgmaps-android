package com.dnovaes.pxgmapsandroid.mapsList.models

import com.dnovaes.pxgmapsandroid.ui.StateProcessInterface
import com.dnovaes.pxgmapsandroid.ui.UIFlowStateInterface
import com.dnovaes.pxgmapsandroid.ui.models.StateStatus
import com.dnovaes.pxgmapsandroid.ui.models.UIErrorInterface

class MapState(
    override val process: MapStateProcess = MapStateProcess.RESET,
    override val status: StateStatus = StateStatus.IDLE,
    override val data: MapStateData = MapStateData(),
    override val error: UIErrorInterface? = null
): UIFlowStateInterface<MapStateProcess, StateStatus, MapStateData> {

    override fun copy(
        process: MapStateProcess,
        state: StateStatus,
        data: MapStateData,
    ): UIFlowStateInterface<MapStateProcess, StateStatus, MapStateData> {
        return MapState(process, state, data)
    }

    override fun withProcess(process: MapStateProcess): MapState {
        return super.withProcess(process) as MapState
    }

    override fun withState(state: StateStatus): MapState {
        return super.withState(state) as MapState
    }

    override fun withData(data: MapStateData): MapState {
        return super.withData(data) as MapState
    }

    //region LoadMaps

    fun isLoadMaps() = (process == MapStateProcess.LOAD_MAPS)

    fun isProcessingLoadMaps() = isLoadMaps() &&
            status == StateStatus.PROCESSING

    fun isDoneLoadMap() = isLoadMaps() &&
            status == StateStatus.DONE

    fun asLoadingMaps(): MapState = this.withProcess(MapStateProcess.LOAD_MAPS)
        .withState(StateStatus.PROCESSING) as MapState

    //endregion
}

enum class MapStateProcess: StateProcessInterface {
    RESET,
    LOAD_MAPS
}
