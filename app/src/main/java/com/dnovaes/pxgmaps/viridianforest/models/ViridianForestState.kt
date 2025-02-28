package com.dnovaes.pxgmaps.viridianforest.models

import com.dnovaes.pxgmaps.ui.StateProcessInterface
import com.dnovaes.pxgmaps.ui.UIFlowStateInterface
import com.dnovaes.pxgmaps.ui.models.StateStatus
import com.dnovaes.pxgmaps.ui.models.UIErrorInterface

class ViridianForestState(
    override val process: ViridianForestStateProcess = ViridianForestStateProcess.RESET,
    override val status: StateStatus = StateStatus.IDLE,
    override val data: ViridianForestStateData = ViridianForestStateData(),
    override val error: UIErrorInterface? = null
): UIFlowStateInterface<ViridianForestStateProcess, StateStatus, ViridianForestStateData> {

    override fun copy(
        process: ViridianForestStateProcess,
        state: StateStatus,
        data: ViridianForestStateData,
    ): UIFlowStateInterface<ViridianForestStateProcess, StateStatus, ViridianForestStateData> {
        return ViridianForestState(process, state, data)
    }

    override fun withProcess(process: ViridianForestStateProcess): ViridianForestState{
        return super.withProcess(process) as ViridianForestState
    }

    override fun withState(state: StateStatus): ViridianForestState {
        return super.withState(state) as ViridianForestState
    }

    override fun withData(data: ViridianForestStateData): ViridianForestState {
        return super.withData(data) as ViridianForestState
    }

    //region LoadingMenu

    fun isLoadMenu() = (process == ViridianForestStateProcess.LOADING_MENU)

    fun isProcessingLoadMenu() = isLoadMenu() &&
            status == StateStatus.PROCESSING

    fun isDoneLoadMenu() = isLoadMenu() &&
            status == StateStatus.DONE

    fun asLoadingMenu(): ViridianForestState = this
        .withProcess(ViridianForestStateProcess.LOADING_MENU)
        .withState(StateStatus.PROCESSING) as ViridianForestState

    //endregion

    //region Play

    fun isGenerateJackpot() = (process == ViridianForestStateProcess.GENERATE_JACKPOT)

    fun asGeneratingJackpot(): ViridianForestState = this
        .withProcess(ViridianForestStateProcess.GENERATE_JACKPOT)
        .withState(StateStatus.PROCESSING)

    fun isGeneratingJackpot() = isGenerateJackpot() &&
            status == StateStatus.PROCESSING

    //endregion
}

enum class ViridianForestStateProcess: StateProcessInterface {
    RESET,
    LOADING_MENU,
    GENERATE_JACKPOT
}
