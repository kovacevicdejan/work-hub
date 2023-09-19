package com.example.workhub.ui.elements.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.workhub.data.retrofit.BASE_URL

@Composable
fun PageImage(
    image_name: String,
    size: Int,
    vertical_padding: Int = 0,
    horizontal_padding: Int = 0
) {
    AsyncImage(
        model = BASE_URL + "image/get_image/" + image_name,
        contentDescription = "profile_image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(vertical = vertical_padding.dp, horizontal = horizontal_padding.dp)
            .size(size.dp)
    )
}