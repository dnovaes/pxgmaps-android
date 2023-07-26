package com.dnovaes.pxgmaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dnovaes.pxgmaps.ui.theme.PxgmapsTheme
import com.dnovaes.pxgmaps.landingPage.viewmodels.LandingPageViewModel
import androidx.compose.runtime.getValue
import com.dnovaes.pxgmaps.landingPage.models.LandingState
import com.dnovaes.pxgmaps.landingPage.models.MenuItem

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PxgmapsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LandingPage()
                }
            }
        }
    }
}

@Composable
fun LandingPage(viewModel: LandingPageViewModel = LandingPageViewModel()) {
    val initialState = LandingState()
    val landingDataState by viewModel.landingData.collectAsState(initialState)
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
            //doNothing
        }
    ) {
        Text(menuItem.title)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PxgmapsTheme {
        Greeting("Android")
    }
}