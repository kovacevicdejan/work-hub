package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.workhub.ui.elements.composables.Post
import com.example.workhub.ui.stateholders.HomeViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun HomeScreen(
    workHubViewModel: WorkHubViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val homeUiState by homeViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.getPosts(email = uiState.curr_user?.email ?: "")
    }

    Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)) {
        LazyColumn {
            item {
                for (post in homeUiState.posts) {
                    Post(
                        navController = navController,
                        workHubViewModel = workHubViewModel,
                        post = post,
                        user = if (post.creator_type == 0) homeUiState.users[post.creator] else null,
                        page = if (post.creator_type == 1) homeUiState.pages[post.creator] else null
                    )
                }
            }
        }
    }
}