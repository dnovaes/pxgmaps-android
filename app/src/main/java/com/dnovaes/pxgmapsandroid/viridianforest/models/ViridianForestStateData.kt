package com.dnovaes.pxgmapsandroid.viridianforest.models

import com.dnovaes.pxgmapsandroid.common.getColumnsMatches
import com.dnovaes.pxgmapsandroid.common.getRowsMatches
import com.dnovaes.pxgmapsandroid.common.model.PokeCell
import com.dnovaes.pxgmapsandroid.ui.StateDataInterface

data class ViridianForestStateData(
    val gridItems: GridItems = GridItems(),
    val rowMatches: List<ItemMatch> = emptyList()
): StateDataInterface

data class GridItems(
    val cellsGrid: List<List<PokeCell>> = listOf()
) {
    fun isEmpty() = cellsGrid.isEmpty()

    fun getCellMatches(): List<ItemMatch> {
        val matches = mutableListOf<ItemMatch>()
        val rowMatches = cellsGrid.getRowsMatches()
        val columnMatches = cellsGrid.getColumnsMatches()
        matches.addAll(rowMatches)
        matches.addAll(columnMatches)

        return matches
    }
}

data class Row(
    val cells: List<PokeCell>
)

data class ItemMatch(
    val rowPos: Int,
    val columnPos: Int,
)

data class ResultMatch(
    val outerMostPosition: Int,
    val numMatches: Int
)