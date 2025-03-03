package com.dnovaes.pxgmapsandroid.viridianforest

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.pxgmapsandroid.common.model.PokeCell
import com.dnovaes.pxgmapsandroid.viridianforest.models.CellInfo
import com.dnovaes.pxgmapsandroid.viridianforest.models.GridItems
import com.dnovaes.pxgmapsandroid.viridianforest.models.RowItem
import com.dnovaes.pxgmapsandroid.viridianforest.models.ViridianForestState
import com.dnovaes.pxgmapsandroid.viridianforest.models.ViridianForestStateData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil

class ViridianForestViewModel: ViewModel() {

    private var localGameState = ViridianForestState()
    val gameState: MutableState<ViridianForestState> = mutableStateOf(localGameState)

    // Controls animation visibility and translateY
    var shouldAnimate = mutableStateOf(false)
        private set

    companion object {
        val mockedGridItems = GridItems(
            rows = listOf(
                RowItem(cells = listOf(
                    CellInfo(PokeCell.enumValue("1")),
                    CellInfo(PokeCell.enumValue("2")),
                    CellInfo(PokeCell.enumValue("3")),
                    CellInfo(PokeCell.enumValue("2")),
                    CellInfo(PokeCell.enumValue("1")),
                )),
                RowItem(cells = listOf(
                    CellInfo(PokeCell.enumValue("5")),
                    CellInfo(PokeCell.enumValue("6")),
                    CellInfo(PokeCell.enumValue("6")),
                    CellInfo(PokeCell.enumValue("6")),
                    CellInfo(PokeCell.enumValue("4")),
                )),
                RowItem(cells = listOf(
                    CellInfo(PokeCell.enumValue("7")),
                    CellInfo(PokeCell.enumValue("8")),
                    CellInfo(PokeCell.enumValue("7")),
                    CellInfo(PokeCell.enumValue("8")),
                    CellInfo(PokeCell.enumValue("9")),
                )),
                RowItem(cells = listOf(
                    CellInfo(PokeCell.enumValue("7")),
                    CellInfo(PokeCell.enumValue("8")),
                    CellInfo(PokeCell.enumValue("7")),
                    CellInfo(PokeCell.enumValue("8")),
                    CellInfo(PokeCell.enumValue("9")),
                )),
                RowItem(cells = listOf(
                    CellInfo(PokeCell.enumValue("7")),
                    CellInfo(PokeCell.enumValue("8")),
                    CellInfo(PokeCell.enumValue("7")),
                    CellInfo(PokeCell.enumValue("8")),
                    CellInfo(PokeCell.enumValue("9")),
                )),
            )
        )
    }

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
                cells.add(CellInfo(PokeCell.enumValue("$id")))
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
        localGameState = localGameState
            .asGeneratedJackpot()
            .withData(model)
        gameState.value = localGameState
        animate()
        postIdleState()
    }

    fun userRequestedPlay() {
        shouldAnimate.value = false
        localGameState = localGameState
            .asGeneratingJackpot()
        gameState.value = localGameState

        viewModelScope.launch(Dispatchers.IO) {
            val model = ViridianForestStateData(gridItems = generateGridValues(5, 5))
            //val model = ViridianForestStateData(gridItems = mockedGridItems)
            delay(300)
            localGameState = localGameState
                .asGeneratedJackpot()
                .withData(model)
            gameState.value = localGameState
            animate()
            delay(400)
            calculateMatches(model.gridItems)
            delay(500)
            postIdleState()
        }
    }

    private fun animate() {
        viewModelScope.launch {
            delay(200) // Small delay to allow recomposition
            shouldAnimate.value = true // Re-trigger animation
        }
    }

    private fun postIdleState() {
        localGameState = localGameState
            .asIdleJackpot()
        gameState.value = localGameState
    }

    private fun calculateMatches(
        gridItems: GridItems
    ) {
        val model = localGameState.data
        val newModel = model.copy(rowMatches = gridItems.getRowMatches())

        localGameState = localGameState
            .asDoneCalculateMatches()
            .withData(newModel)
        gameState.value = localGameState
    }
}
