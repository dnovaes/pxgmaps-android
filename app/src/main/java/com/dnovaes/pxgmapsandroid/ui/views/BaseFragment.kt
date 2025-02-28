package com.dnovaes.pxgmapsandroid.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    @Composable
    fun showSpinner() {
        AnimatedVisibility(
            visible = true,
            enter = slideInVertically(
                animationSpec = tween(1000)
            ),
            exit = fadeOut(
                animationSpec = tween(1000)
            ) + slideOutVertically()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    color = Color.Blue,
                    strokeWidth = 6.dp,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }

}