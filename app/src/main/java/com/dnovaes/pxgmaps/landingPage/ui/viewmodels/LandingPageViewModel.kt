package com.dnovaes.pxgmaps.landingPage.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dnovaes.pxgmaps.landingPage.ui.models.LandingState
import com.dnovaes.pxgmaps.landingPage.ui.models.LandingStateData
import com.dnovaes.pxgmaps.landingPage.ui.models.MenuItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class LandingPageViewModel: ViewModel() {
    private var landingState = LandingState()

    //private val _landingData = MutableStateFlow(LandingState())
    //val landingData: Flow<LandingState> = _landingData

    val landingData: MutableState<LandingState> = mutableStateOf(landingState)

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
        landingState = landingState
            .asLoadingMenu()
            .withData(model)
        landingData.value = landingState
    }
}
