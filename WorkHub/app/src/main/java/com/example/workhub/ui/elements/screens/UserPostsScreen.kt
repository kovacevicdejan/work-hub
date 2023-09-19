package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.workhub.ui.elements.composables.Post
import com.example.workhub.ui.stateholders.UserPostsViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun UserPostsScreen(
    workHubViewModel: WorkHubViewModel,
    userPostsViewModel: UserPostsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val userPostsUiState by userPostsViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        userPostsViewModel.getPosts(uiState.user)
    }

    LazyColumn {
        item {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Posts",
                    modifier = Modifier.weight(1f),
                    fontSize = 30.sp
                )
            }
        }

        for(post in userPostsUiState.posts) {
            item {
                userPostsUiState.user?.let {
                    Post(
                        post = post,
                        creator = it,
                        navController = navController,
                        curr_user = uiState.curr_user?.email ?: ""
                    )
                }
            }
        }
    }
}