package com.example.workhub.ui.elements.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.workhub.R
import com.example.workhub.ui.elements.composables.CommentCard
import com.example.workhub.ui.elements.composables.Post
import com.example.workhub.ui.elements.composables.ProfileImage
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.CommentsViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CommentsScreen(
    workHubViewModel: WorkHubViewModel,
    commentsViewModel: CommentsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val commentsUiState by commentsViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        commentsViewModel.getPost(uiState.post_id)
    }

    Scaffold(
        bottomBar = {
            Card(
                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        OutlinedTextField(
                            value = commentsUiState.text,
                            onValueChange = {commentsViewModel.setText(it)},
                            modifier = Modifier
                                .weight(5.6f)
                                .padding(horizontal = 10.dp)
                        )

                        Button(
                            onClick = {
                                commentsViewModel.addComment((uiState.curr_user?.firstname ?: "") + " " + (uiState.curr_user?.lastname ?: ""), uiState.curr_user?.profile_image ?: "")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = null)
                        }
                    }

                    Divider()
                }
            }
        }
    ) {
        LazyColumn {
            item {
                if(commentsUiState.post != null) {
                    Post(
                        post = commentsUiState.post!!,
                        user = commentsUiState.user,
                        page = commentsUiState.page,
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }
            }

            item {
                Text(
                    text = "Comments",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp)
                )

                if(commentsUiState.post != null) {
                    for (comment in commentsUiState.post!!.comments) {
                        CommentCard(comment = comment, commentsViewModel = commentsViewModel)
                    }
                }
            }
        }
    }
}