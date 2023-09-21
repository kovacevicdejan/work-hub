package com.example.workhub.ui.elements.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workhub.HomeDestination
import com.example.workhub.data.retrofit.models.PageReview
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.ManageNetworkViewModel
import com.example.workhub.ui.stateholders.PageViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun ReviewCard(
    review: PageReview,
    first: Boolean
) {
    Card(
        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
            0xFFEEEEEE
        ),
        modifier = Modifier
            .padding(
                0.dp,
                0.dp,
                0.dp,
                if (first) 80.dp else 0.dp
            )
            .fillMaxWidth(),
        shape = Shapes.large
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage(image_name = review.user_image, size = 30, vertical_padding = 5, horizontal_padding = 5)

                Text(text = review.user, fontSize = 20.sp)
            }

            Column(modifier = Modifier.padding(40.dp, 0.dp, 0.dp, 5.dp)) {
                Text(
                    text = review.text,
                    fontSize = 18.sp,
                    maxLines = Int.MAX_VALUE
                )
            }

            Divider()
        }
    }
}