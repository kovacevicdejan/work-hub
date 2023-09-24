package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.workhub.ui.elements.composables.Post
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.stateholders.HomeViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun HomeScreen(
    workHubViewModel: WorkHubViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    homeViewModel.setTimestamp()
    val posts =
        homeViewModel.getPosts(email = uiState.curr_user?.email ?: "").collectAsLazyPagingItems()

    Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)) {
        LazyColumn {
            items(count = posts.itemCount) { index ->
                posts[index]?.let {
                    Post(
                        navController = navController,
                        workHubViewModel = workHubViewModel,
                        post = it.post,
                        user = it.user,
                        page = it.page
                    )
                }
            }

            when (posts.loadState.refresh) {
                is LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = "Refresh Loading"
                            )

                            CircularProgressIndicator(color = Blue)
                        }
                    }
                }
                else -> {}
            }

            when (posts.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(text = "Loading")

                            CircularProgressIndicator(color = Blue)
                        }
                    }
                }
                else -> {}
            }
        }
    }
}