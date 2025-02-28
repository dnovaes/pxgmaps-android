package com.dnovaes.pxgmapsandroid.landingPage.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.dnovaes.pxgmapsandroid.mapsList.models.MapItem
import com.dnovaes.pxgmapsandroid.utils.ImageUtils

@Composable
fun MapCard(
    item: MapItem,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        val bitmap = ImageUtils.loadBitmap(url = item.url).value
        Column {
            bitmap?.let { loadedImage ->
                Image(
                    bitmap = loadedImage.asImageBitmap(),
                    contentDescription = "a Pxg Map",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 4.dp,
                        bottom = 6.dp,
                        start = 6.dp,
                        end = 6.dp
                    )
            ) {
               Text(
                   text = item.title,
                   modifier = Modifier
                       .fillMaxWidth(0.65f)
                       .wrapContentSize(Alignment.CenterStart),
                   style = MaterialTheme.typography.h6
               )
                Text(
                    text = item.coordinates,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.CenterEnd),
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}
