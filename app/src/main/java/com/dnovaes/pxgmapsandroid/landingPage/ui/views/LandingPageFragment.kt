package com.dnovaes.pxgmapsandroid.landingPage.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dnovaes.pxgmapsandroid.R
import com.dnovaes.pxgmapsandroid.landingPage.ui.models.MenuItem
import com.dnovaes.pxgmapsandroid.landingPage.ui.viewmodels.LandingPageViewModel


class LandingPageFragment: Fragment() {

    private val viewModel by viewModels<LandingPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LandingPage()
            }
        }
    }

    @Composable
    fun LandingPage() {
        val landingDataState = viewModel.landingState.value
        when {
            landingDataState.isLoadMenu() -> LandingPageMenu(landingDataState.data.menuItems)
        }
    }

    @Composable
    fun LandingPageMenu(menuItemsData: List<MenuItem>) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            menuItems(menuItemsData)
        }
    }

    @Composable
    fun menuItems(list: List<MenuItem>) {
        list.forEachIndexed { index, menuItem ->
            menuButton(index, menuItem)
        }
    }

    @Composable
    fun menuButton(i: Int, menuItem: MenuItem) {
        Button(
            modifier = Modifier.padding(vertical = 8.dp),
            onClick = {
                if (menuItem.numClicks == 1) {
                    findNavController().navigate(R.id.action_landingPageFragment_to_mapsListFragment)
                } else {
                    viewModel.userClickedOnMenuItem(i)
                }
            }
        ) {
            val textValue = if (menuItem.numClicks > 0) {
                "${menuItem.title} ${menuItem.numClicks}"
            } else {
                menuItem.title
            }
            Text(textValue)
        }
    }
}