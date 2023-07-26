package com.dnovaes.pxgmaps.landingPage.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dnovaes.pxgmaps.landingPage.ui.models.LandingState
import com.dnovaes.pxgmaps.landingPage.ui.models.LandingStateData
import com.dnovaes.pxgmaps.landingPage.ui.models.MenuItem

class LandingPageViewModel: ViewModel() {

    private var localLandingState = LandingState()
    val landingState: MutableState<LandingState> = mutableStateOf(localLandingState)

    init {
        loadInitialItems()
    }

    private fun loadInitialItems() {
        val menuItems = listOf(
            MenuItem("Kanto"),
            MenuItem("Outland / Phenac"),
            MenuItem("Nightmare"),
        )
        val model = LandingStateData(menuItems = menuItems)
        localLandingState = localLandingState
            .asLoadingMenu()
            .withData(model)
        landingState.value = localLandingState
    }

    fun userClickedOnMenuItem(index: Int) {
        val landingData = localLandingState.data
        val currentMenuItems = localLandingState.data.menuItems.toMutableList()

        if (currentMenuItems.isNotEmpty()) {
            val currentMenuItem = currentMenuItems[index]
            val newMenuItem = currentMenuItem.copy(numClicks = currentMenuItem.numClicks + 1)
            currentMenuItems[index] = newMenuItem

            localLandingState = localLandingState
                .withData(landingData.copy(menuItems = currentMenuItems.toList()))
            landingState.value = localLandingState
        }
    }
}
