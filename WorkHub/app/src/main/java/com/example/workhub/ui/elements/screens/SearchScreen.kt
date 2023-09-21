package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.workhub.ui.elements.composables.SearchJobCard
import com.example.workhub.ui.elements.composables.SearchPageCard
import com.example.workhub.ui.elements.composables.SearchUserCard
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.SearchViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun SearchScreen(
    workHubViewModel: WorkHubViewModel,
    searchViewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("PEOPLE", "PAGES", "JOBS")
    val uiState by workHubViewModel.uiState.collectAsState()
    val searchUiState by searchViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        searchViewModel.searchUsers(uiState.keyword)
        searchViewModel.searchPages(uiState.keyword)
        searchViewModel.searchJobs(uiState.keyword)
    }

    LazyColumn {
        item {
            TabRow(
                selectedTabIndex = state,
                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                    0xFFEEEEEE
                )
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = state == index,
                        onClick = { state = index }
                    )
                }
            }
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
            ) {
                Text(text = "${searchUiState.users.size + searchUiState.jobs.size + searchUiState.pages.size} search results")
            }
        }

        when (state) {
            0 -> {
                item {
                    for(user in searchUiState.users) {
                        SearchUserCard(user = user, navController = navController, workHubViewModel = workHubViewModel)

                        Divider()
                    }
                }
            }

            1 -> {
                item {
                    for(page in searchUiState.pages) {
                        SearchPageCard(page = page, navController = navController, workHubViewModel = workHubViewModel)

                        Divider()
                    }
                }
            }

            2 -> {
                for (job in searchUiState.jobs) {
                    item {
                        SearchJobCard(job = job, navController = navController, workHubViewModel = workHubViewModel)

                        Divider()
                    }
                }
            }
        }
    }
}