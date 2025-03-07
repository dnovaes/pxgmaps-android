package com.dnovaes.pxgmapsandroid.common

import com.dnovaes.pxgmapsandroid.common.model.PokeCell
import com.dnovaes.pxgmapsandroid.viridianforest.models.ItemMatch
import com.dnovaes.pxgmapsandroid.viridianforest.models.ResultMatch

fun List<ItemMatch>.hasMatchWithPos(
    rowPos: Int,
    columnPos: Int
): Boolean
    = firstOrNull { rowPos == it.rowPos && columnPos == it.columnPos } != null

fun List<PokeCell>.getMatch(minMatchCount: Int, listSize: Int): ResultMatch? {
    // minMatchCount = 2
    // 2 successful matches between before and next equivalent to 3 in total
    var i = 0
    var countMatches = 0
    var rightMostPos: Int = -1

    while (i < listSize - 1) {
        if (this[i].id == this[i+1].id) {
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

fun List<List<PokeCell>>.getRowsMatches(): List<ItemMatch> {
    val matches = mutableListOf<ItemMatch>()
    forEachIndexed { rowPos, row ->
        row.getMatch(minMatchCount = 2, row.size)?.let { match ->
            val initPos = match.outerMostPosition - match.numMatches
            for (columnPos in initPos .. match.outerMostPosition) {
                matches.add(
                    ItemMatch(
                        rowPos = rowPos,
                        columnPos = columnPos
                    )
                )
            }
        }
    }
    return matches.toList()
}

fun List<List<PokeCell>>.getColumnsMatches(): List<ItemMatch> {
    val matches = mutableListOf<ItemMatch>()
    val listSize = this.size

    for (colPos in 0 until listSize) {
        val column = mutableListOf<PokeCell>()
        for (rowPos in 0 until listSize) {
            column.add(this[rowPos][colPos])
        }

        column.getMatch(minMatchCount = 2, listSize)?.let { match ->
            val initPos = match.outerMostPosition - match.numMatches
            for (pos in initPos .. match.outerMostPosition) {
                matches.add(
                    ItemMatch(
                        rowPos = pos,
                        columnPos = colPos
                    )
                )
            }
        }
    }

    return matches.toList()
}
