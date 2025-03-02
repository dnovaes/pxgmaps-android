package com.dnovaes.pxgmapsandroid.viridianforest.models

import androidx.annotation.DrawableRes
import com.dnovaes.pxgmapsandroid.R
import com.dnovaes.pxgmapsandroid.common.model.PokeCell
import com.dnovaes.pxgmapsandroid.ui.StateDataInterface

data class ViridianForestStateData(
    val gridItems: GridItems = GridItems(listOf())
): StateDataInterface

data class GridItems(
    val rows: List<RowItem>
) {
    fun isEmpty() = rows.isEmpty()
}

data class RowItem(
    val cells: List<CellInfo>
)

data class CellInfo(
    val id: String,
    val pokeCell: PokeCell
)