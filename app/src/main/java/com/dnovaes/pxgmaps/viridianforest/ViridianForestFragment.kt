package com.dnovaes.pxgmaps.viridianforest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.dnovaes.pxgmaps.landingPage.ui.models.MenuItem
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dnovaes.pxgmaps.BaseFragment
import com.dnovaes.pxgmaps.R


class ViridianForestFragment: BaseFragment() {

    override fun fragmentName(): String
        = this.javaClass.simpleName

    private val viewModel by viewModels<ViridianForestViewModel>()

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
        val dataState = viewModel.landingState.value
        when {
            dataState.isLoadMenu() ||
            dataState.isGeneratingJackpot() ->
                ViridianForestPage(dataState.data.menuItems)
        }
    }

    @Composable
    fun ViridianForestPage(menuItems: List<List<MenuItem>>) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            JackpotGrid(menuItems)
        }
    }

    @Composable
    fun GridExample() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val items = (1..9).chunked(3) // Splitting into groups of 3 to form rows

            items.forEach { rowItems ->
                Row(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(80.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Item $item")
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewGridExample() {
        GridExample()
    }


    @Composable
    fun JackpotGrid(menuItemsData: List<List<MenuItem>>) {
        menuItemsData.forEach { rowItems ->
            JackpotGridRows(rowItems)
        }

        Spacer(modifier = Modifier.height(16.dp))

        JackpotButton()
    }

    @Composable
    fun JackpotGridRows(rowItems: List<MenuItem>) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            rowItems.forEachIndexed { i, item ->
                cellInfo(i = i, item = item)
            }
        }
    }

    @Composable
    fun cellInfo(i: Int, item: MenuItem) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = item.title)
        }
    }

    @Composable
    fun JackpotButton() {
        Button(
            modifier = Modifier
                .width(160.dp)
                .height(40.dp),
            onClick = {
                viewModel.userRequestedPlay()
            }
        ) {
            Text(stringResource(R.string.walk_through))
        }
    }
}