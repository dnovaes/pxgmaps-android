package com.dnovaes.pxgmapsandroid.common

import com.dnovaes.pxgmapsandroid.viridianforest.models.ItemMatch

fun List<ItemMatch>.hasMatchWithPos(
    rowPos: Int,
    columnPos: Int
): Boolean
    = firstOrNull { rowPos == it.rowPos && columnPos == it.columnPos } != null