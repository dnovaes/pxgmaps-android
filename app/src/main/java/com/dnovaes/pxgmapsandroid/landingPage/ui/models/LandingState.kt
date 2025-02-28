package com.dnovaes.pxgmapsandroid.landingPage.ui.models

import com.dnovaes.pxgmapsandroid.ui.StateProcessInterface
import com.dnovaes.pxgmapsandroid.ui.UIFlowStateInterface
import com.dnovaes.pxgmapsandroid.ui.models.StateStatus
import com.dnovaes.pxgmapsandroid.ui.models.UIErrorInterface

class LandingState(
    override val process: LandingStateProcess = LandingStateProcess.RESET,
    override val status: StateStatus = StateStatus.IDLE,
    override val data: LandingStateData = LandingStateData(),
    override val error: UIErrorInterface? = null
): UIFlowStateInterface<LandingStateProcess, StateStatus, LandingStateData> {

    override fun copy(
        process: LandingStateProcess,
        state: StateStatus,
        data: LandingStateData,
    ): UIFlowStateInterface<LandingStateProcess, StateStatus, LandingStateData> {
        return LandingState(process, state, data)
    }

    override fun withProcess(process: LandingStateProcess): LandingState {
        return super.withProcess(process) as LandingState
    }

    override fun withState(state: StateStatus): LandingState {
        return super.withState(state) as LandingState
    }

    override fun withData(data: LandingStateData): LandingState {
        return super.withData(data) as LandingState
    }

    //region LoadingMenu

    fun isLoadMenu() = (process == LandingStateProcess.LOADING_MENU)

    fun isProcessingLoadMenu() = isLoadMenu() &&
            status == StateStatus.PROCESSING

    fun isDoneLoadMenu() = isLoadMenu() &&
            status == StateStatus.DONE

    fun asLoadingMenu(): LandingState = this.withProcess(LandingStateProcess.LOADING_MENU)
        .withState(StateStatus.PROCESSING) as LandingState

    //endregion
}

data class MenuItem(
    val title: String,
    val numClicks: Int = 0
)

enum class LandingStateProcess: StateProcessInterface {
    RESET,
    LOADING_MENU
}
