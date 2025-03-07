package com.dnovaes.pxgmapsandroid.viridianforest.models

import com.dnovaes.pxgmapsandroid.common.model.PokeCell
import com.dnovaes.pxgmapsandroid.ui.StateDataInterface

data class ViridianForestStateData(
    val gridItems: GridItems = GridItems(listOf()),
    val rowMatches: List<ItemMatch> = emptyList()
): StateDataInterface

data class GridItems(
    val rows: List<RowItem>
) {
    fun isEmpty() = rows.isEmpty()

    fun getRowMatches(): List<ItemMatch> {
        val rowMatches = mutableListOf<ItemMatch>()
        rows.forEachIndexed { rowPos, row ->
            row.getMatch(minMatchCount = 2, rows.size)?.let { match ->
                val initPos = match.rightMostPosition - match.numMatches
                for (columnPos in initPos .. match.rightMostPosition) {
                    rowMatches.add(
                        ItemMatch(
                            rowPos = rowPos,
                            columnPos = columnPos
                        )
                    )
                }
            }
        }
        return rowMatches
    }
}

data class RowItem(
    val cells: List<PokeCell>
) {

    fun getMatch(minMatchCount: Int, rowSize: Int): ResultMatch? {
        // minMatchCount = 2
        // 2 successful matches between before and next equivalent to 3 in total
        var i = 0
        var countMatches = 0
        var rightMostPos: Int = -1

        while (i < rowSize - 1) {
            if (cells[i].id == cells[i+1].id) {
                countMatches ++

                if (countMatches >= minMatchCount) {
                    rightMostPos = i + 1
                }
            } else {
                if (countMatches >= minMatchCount) {
                    rightMostPos = i
                    break
                }
                countMatches = 0
            }
            i++
        }

        return if (rightMostPos > -1) {
            ResultMatch(rightMostPos, countMatches)
        } else {
            null
        }
    }
}

data class ColumnItem(
    val cells: List<PokeCell>
)

data class ItemMatch(
    val rowPos: Int,
    val columnPos: Int,
)

data class ResultMatch(
    val rightMostPosition: Int,
    val numMatches: Int
)