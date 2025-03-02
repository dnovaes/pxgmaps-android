package com.dnovaes.pxgmapsandroid.common.model

import androidx.annotation.DrawableRes
import com.dnovaes.pxgmapsandroid.R

enum class PokeCell(
    private val id: String,
    @DrawableRes val image: Int
) {
    UNKNOWN("0", R.drawable.cell_unknown),
    CATERPIE("1", R.drawable.cell_caterpie),
    METAPOD("2", R.drawable.cell_metapod),
    WEEDLE("3", R.drawable.cell_caterpie),
    KAKUNA("4", R.drawable.cell_kakuna),
    BEEDRILL("5", R.drawable.cell_beedril),
    HOOTHOOT("6", R.drawable.cell_hoothoot),
    PIKACHU("7", R.drawable.cell_pikachu_smiling),
    PINSIR("8", R.drawable.cell_pinsir);

    companion object {
        fun enumValue(id: String): PokeCell
                = PokeCell.values().firstOrNull { it.id == id } ?: UNKNOWN
    }
}
