package com.dnovaes.pxgmapsandroid.viridianforest

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dnovaes.pxgmapsandroid.common.model.PokeCell
import com.dnovaes.pxgmapsandroid.landingPage.ui.models.MenuItem
import com.dnovaes.pxgmapsandroid.viridianforest.models.CellInfo
import com.dnovaes.pxgmapsandroid.viridianforest.models.GridItems
import com.dnovaes.pxgmapsandroid.viridianforest.models.RowItem
import com.dnovaes.pxgmapsandroid.viridianforest.models.ViridianForestState
import com.dnovaes.pxgmapsandroid.viridianforest.models.ViridianForestStateData
import kotlin.math.ceil

class ViridianForestViewModel: ViewModel() {

    private var localViridianForestState = ViridianForestState()
    val landingState: MutableState<ViridianForestState> = mutableStateOf(localViridianForestState)

    init {
        loadInitialItems()
    }

    private fun generateGridValues(
        rowQty: Int,
        columnQty: Int
    ): GridItems {
        val rowItems = mutableListOf<RowItem>()
        for (i in 1 .. rowQty) {
            val cells = mutableListOf<CellInfo>()
            for (j in 1 .. columnQty) {
                val id = generateNumber()
                cells.add(CellInfo("$id", PokeCell.enumValue("$id")))
            }
            rowItems.add(RowItem(cells.toList()))
        }
        return GridItems(rows = rowItems.toList())
    }


    private fun generateNumber(): Int {
        //toInt = rounded down
        return ceil(8 * Math.random()).toInt()
    }

    private fun loadInitialItems() {
        val menuItems = generateGridValues(5, 5)
        val model = ViridianForestStateData(gridItems = menuItems)
        localViridianForestState = localViridianForestState
            .asLoadingMenu()
            .withData(model)
        landingState.value = localViridianForestState
    }

    fun userRequestedPlay() {
        val model = ViridianForestStateData(gridItems = generateGridValues(5, 5))
        localViridianForestState = localViridianForestState
            .asGeneratingJackpot()
            .withData(model)
        landingState.value = localViridianForestState
    }
}
