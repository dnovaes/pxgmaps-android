package com.dnovaes.pxgmapsandroid.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object ImageUtils {

    @Composable
    fun loadBitmap(
        url: String,
    ): MutableState<Bitmap?> {
        val bitmapState: MutableState<Bitmap?> = remember { mutableStateOf<Bitmap?>(null) }

        Glide.with(LocalContext.current)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapState.value = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    //do nothing
                }
            })
        return bitmapState
    }
}