package com.dnovaes.pxgmaps.viridianforest

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dnovaes.pxgmaps.landingPage.ui.models.MenuItem
import com.dnovaes.pxgmaps.viridianforest.models.ViridianForestState
import com.dnovaes.pxgmaps.viridianforest.models.ViridianForestStateData
import kotlin.math.ceil

class ViridianForestViewModel: ViewModel() {

    private var localViridianForestState = ViridianForestState()
    val landingState: MutableState<ViridianForestState> = mutableStateOf(localViridianForestState)

    init {
        loadInitialItems()
    }

    private fun generateGridValues(): List<List<MenuItem>> {
        val values = mutableListOf<MenuItem>()
        for (i in 1..9) {
            val cellInfo = MenuItem("${generateNumber()}")
            values.add(cellInfo)
        }
        return values.toList().chunked(3)
    }

    private fun generateNumber(): Int {
        //toInt = rounded down
        return ceil(8 * Math.random()).toInt()
    }

    private fun loadInitialItems() {
        val menuItems = generateGridValues()
        val model = ViridianForestStateData(menuItems = menuItems)
        localViridianForestState = localViridianForestState
            .asLoadingMenu()
            .withData(model)
        landingState.value = localViridianForestState
    }

    fun userRequestedPlay() {
        val model = ViridianForestStateData(menuItems = generateGridValues())
        localViridianForestState = localViridianForestState
            .asGeneratingJackpot()
            .withData(model)
        landingState.value = localViridianForestState
    }
}
