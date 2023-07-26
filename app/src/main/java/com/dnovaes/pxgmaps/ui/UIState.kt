package com.dnovaes.pxgmaps.ui

interface UIFlowStateInterface<
        P: StateProcessInterface,
        S: StateStatus,
        D: StateDataInterface> {
    val process: P
    val status: S
    val data: D

    fun copy(
        process: P = this.process,
        state: S = this.status,
        data: D = this.data
    ): UIFlowStateInterface<P, S, D>

    fun withProcess(process: P): UIFlowStateInterface<P, S, D> =
        this.copy(process = process)

    fun withState(state: S): UIFlowStateInterface<P, S, D> =
        this.copy(state = state)

    fun withData(data: D): UIFlowStateInterface<P, S, D> =
        this.copy(data = data)
}

interface StateDataInterface

interface StateProcessInterface

enum class StateStatus {
    IDLE,
    START,
    PROCESSING,
    DONE
}
