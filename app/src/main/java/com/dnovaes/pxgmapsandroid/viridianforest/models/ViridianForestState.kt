package com.dnovaes.pxgmapsandroid.viridianforest.models

import com.dnovaes.pxgmapsandroid.ui.StateProcessInterface
import com.dnovaes.pxgmapsandroid.ui.UIFlowStateInterface
import com.dnovaes.pxgmapsandroid.ui.models.StateStatus
import com.dnovaes.pxgmapsandroid.ui.models.UIErrorInterface

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

    //region Play

    fun isGenerateJackpot() = (process == ViridianForestStateProcess.GENERATE_JACKPOT)

    fun asIdleJackpot(): ViridianForestState = this
        .withProcess(ViridianForestStateProcess.GENERATE_JACKPOT)
        .withState(StateStatus.IDLE)

    fun asGeneratingJackpot(): ViridianForestState = this
        .withProcess(ViridianForestStateProcess.GENERATE_JACKPOT)
        .withState(StateStatus.PROCESSING)

    fun asGeneratedJackpot(): ViridianForestState = this
        .withProcess(ViridianForestStateProcess.GENERATE_JACKPOT)
        .withState(StateStatus.DONE)

    fun isIdleGenerateJackpot() = isGenerateJackpot() &&
            status == StateStatus.IDLE

    fun isProcessingGenerateJackpot() = isGenerateJackpot() &&
            status == StateStatus.PROCESSING

    fun isDoneGenerateJackpot() = isGenerateJackpot() &&
            status == StateStatus.DONE

    //endregion

    //region CalculateMatches

    private fun isCalculateMatches() = (process == ViridianForestStateProcess.CALCULATE_MATCHES)

    fun asDoneCalculateMatches(): ViridianForestState = this
        .withProcess(ViridianForestStateProcess.CALCULATE_MATCHES)
        .withState(StateStatus.DONE)

    fun isDoneCalculateMatches() = isCalculateMatches() &&
            status == StateStatus.DONE

    //endregion

}

enum class ViridianForestStateProcess: StateProcessInterface {
    RESET,
    GENERATE_JACKPOT,
    CALCULATE_MATCHES
}
