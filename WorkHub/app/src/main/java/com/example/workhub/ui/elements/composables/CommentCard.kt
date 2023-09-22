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
import com.example.workhub.data.retrofit.models.Comment
import com.example.workhub.data.retrofit.models.PageReview
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.CommentsViewModel
import com.example.workhub.ui.stateholders.ManageNetworkViewModel
import com.example.workhub.ui.stateholders.PageViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun CommentCard(
    comment: Comment,
    commentsViewModel: CommentsViewModel
) {
    val commentsUiState by commentsViewModel.uiState.collectAsState()

    Card(
        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
            0xFFEEEEEE
        ),
        modifier = Modifier
            .padding(
                10.dp,
                0.dp,
                10.dp,
                if (comment == commentsUiState.post!!.comments[commentsUiState.post!!.comments.size - 1]) 85.dp else 10.dp
            )
            .fillMaxWidth()
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage(image_name = comment.profile_image, size = 30, horizontal_padding = 5, vertical_padding = 5)

                Text(text = comment.user, fontSize = 20.sp)
            }

            Column(modifier = Modifier.padding(30.dp, 0.dp, 0.dp, 5.dp)) {
                Text(
                    text = comment.text,
                    fontSize = 18.sp,
                    maxLines = Int.MAX_VALUE
                )
            }
        }
    }
}