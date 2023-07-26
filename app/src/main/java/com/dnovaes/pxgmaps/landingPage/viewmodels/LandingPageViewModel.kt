package com.dnovaes.pxgmaps.landingPage.viewmodels

import androidx.lifecycle.ViewModel
import com.dnovaes.pxgmaps.landingPage.models.LandingState
import com.dnovaes.pxgmaps.landingPage.models.LandingStateData
import com.dnovaes.pxgmaps.landingPage.models.MenuItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class LandingPageViewModel: ViewModel() {
    private val _landingData = MutableStateFlow(LandingState())
    val landingData: Flow<LandingState> = _landingData

    private var landingState = LandingState()

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
        _landingData.value = landingState
    }
}
