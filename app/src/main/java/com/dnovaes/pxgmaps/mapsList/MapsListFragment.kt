package com.dnovaes.pxgmaps.mapsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.dnovaes.pxgmaps.landingPage.ui.components.MapCard
import com.dnovaes.pxgmaps.mapsList.models.MapItem
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
    fun LoadMenuList(mapItems: List<MapItem>) {
        LazyColumn(
            modifier = Modifier
                .background(color = Color(0xFFDDDDDC))
                .padding(vertical = 6.dp)
        ) {
            itemsIndexed(items = mapItems) {_, item ->
                MapCard(item = item) {
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
