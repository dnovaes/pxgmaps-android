package com.dnovaes.pxgmaps.mapsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.dnovaes.pxgmaps.landingPage.ui.components.MapCard
import com.dnovaes.pxgmaps.mapsList.viewmodels.MapListViewModel
import com.dnovaes.pxgmaps.ui.views.BaseFragment

class MapsListFragment : BaseFragment() {

    private val viewModel: MapListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MapsListPage()
            }
        }
    }

    @Composable
    fun MapsListPage() {
        val state = viewModel.mapState.value
        when {
            state.isProcessingLoadMaps() -> showSpinner()
            state.isDoneLoadMap() -> {
                LoadMenuList(state.data.mapItems)
            }
        }
    }

    @Composable
    fun LoadMenuList(mapItems: List<String>) {
        LazyColumn {
            itemsIndexed(items = mapItems) {_, item ->
                MapCard(mapTitle = item) {
                    Toast.makeText(
                        requireContext(),
                        "$item clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}