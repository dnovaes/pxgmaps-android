package com.dnovaes.pxgmapsandroid.viridianforest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.dnovaes.pxgmapsandroid.BaseFragment
import com.dnovaes.pxgmapsandroid.R
import com.dnovaes.pxgmapsandroid.common.hasMatchWithPos
import com.dnovaes.pxgmapsandroid.common.model.PokeCell
import com.dnovaes.pxgmapsandroid.viridianforest.models.ItemMatch
import com.dnovaes.pxgmapsandroid.viridianforest.models.RowItem
import com.dnovaes.pxgmapsandroid.viridianforest.models.ViridianForestState
import com.dnovaes.pxgmapsandroid.viridianforest.models.ViridianForestStateData
import kotlinx.coroutines.delay


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
        val state = viewModel.gameState.value
        when {
            state.isGenerateJackpot() ||
            state.isDoneCalculateMatches() ->
                ViridianForestPage(state)
        }
    }

    @Composable
    fun ViridianForestPage(state: ViridianForestState) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            JackpotGrid(state)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewGridExample() {
        val mockedGridItems = ViridianForestViewModel.mockedGridItems
        val data = ViridianForestStateData(gridItems = mockedGridItems)
        val state = ViridianForestState(data = data)
        ViridianForestPage(state)
    }


    @Composable
    fun JackpotGrid(state: ViridianForestState) {
        val dataState = state.data
        dataState.gridItems.rows.forEachIndexed { rowPos, rowItem ->
            JackpotGridRows(rowPos, rowItem, dataState.rowMatches)
        }

        Spacer(modifier = Modifier.height(16.dp))

        JackpotButton(
            isEnabled = state.isIdleGenerateJackpot()
        )
    }

    @Composable
    fun JackpotGridRows(
        rowPos: Int,
        rowItem: RowItem,
        matches: List<ItemMatch>
    ) {
        Row(
            modifier = Modifier.padding(2.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            rowItem.cells.forEachIndexed { columnPos, item ->
                cellInfo(
                    columnPos = columnPos,
                    item = item,
                    isMatch = matches.hasMatchWithPos(rowPos, columnPos)
                )
            }
        }
    }

    @Composable
    fun cellInfo(
        columnPos: Int,
        item: PokeCell,
        isMatch: Boolean
    ) {
        val cellSize = 60.dp

        val gameState = viewModel.gameState.value

        if (gameState.isDoneGenerateJackpot()) {
            FirstAppearanceImageBox(cellSize, item)
        } else if(
            gameState.isDoneCalculateMatches() ||
            gameState.isIdleGenerateJackpot()
        ) {
            if (isMatch) {
                BlinkableImageBox(cellSize, item)
            } else {
                StaticImageBox(
                    cellSize = cellSize,
                    item = item,
                    alpha = 1f
                )
            }
        } else {
            EmptyImageBox(cellSize, item)
        }

    }

    @Composable
    fun FirstAppearanceImageBox(
        cellSize: Dp,
        item: PokeCell
    ) {
        val shouldAnimate by viewModel.shouldAnimate

        val translationY by animateFloatAsState(
            targetValue = if (shouldAnimate) 0f else -300f, // Move from -300f (off-screen) to 0f
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessMedium
            ),
            label = "spring spec"
        )

        val alpha by animateFloatAsState(
            targetValue = if (shouldAnimate) 1f else 0f, // Fade in
            animationSpec = tween(durationMillis = 500), // Smooth fade in
            label = "alpha animation"
        )

        Box(
            modifier = Modifier
                .padding(2.dp)
                .size(cellSize),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = item.image),
                contentDescription = "cell_image_${item.id}",
                modifier = Modifier
                    .size(cellSize)
                    .size(200.dp)
                    .graphicsLayer(
                        translationY = translationY,
                        alpha = alpha
                    )
            )
        }
    }

    @Composable
    fun EmptyImageBox(
        cellSize: Dp,
        item: PokeCell
    ) {
        StaticImageBox(
            cellSize = cellSize,
            item = item,
            alpha = 0f,
        )
    }

    @Composable
    fun BlinkableImageBox(
        cellSize: Dp,
        item: PokeCell
    ) {
        var isVisible by remember { mutableStateOf(true) } // Track visibility

        val alpha by animateFloatAsState(
            targetValue = if (isVisible) 1f else 0f, // Fade in & out
            animationSpec = tween(durationMillis = 400),
            label = "blink animation"
        )

        LaunchedEffect(4) {
            repeat(4) { // Blink 4 times
                isVisible = false
                delay(200)
                isVisible = true
                delay(200)
            }
        }

        Box(
            modifier = Modifier
                .padding(2.dp)
                .size(cellSize),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = item.image),
                contentDescription = "cell_image_${item.id}",
                modifier = Modifier
                    .size(cellSize)
                    .size(200.dp)
                    .graphicsLayer(alpha = alpha)
            )
        }
    }

    @Composable
    fun StaticImageBox(
        cellSize: Dp,
        item: PokeCell,
        alpha: Float
    ) {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .size(cellSize),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = item.image),
                contentDescription = "cell_image_${item.id}",
                modifier = Modifier
                    .size(cellSize)
                    .size(200.dp)
                    .alpha(alpha)
            )
        }
    }

    @Composable
    fun JackpotButton(
        isEnabled: Boolean
    ) {
        Button(
            modifier = Modifier
                .width(160.dp)
                .height(40.dp),
            onClick = {
                viewModel.userRequestedPlay()
            },
            enabled = isEnabled,
        ) {
            Text(stringResource(R.string.play))
        }
    }
}