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
import com.example.workhub.ui.elements.composables.ConnectionCard
import com.example.workhub.ui.elements.composables.FollowedPageCard
import com.example.workhub.ui.elements.composables.ProfileImage
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.*

@Composable
fun ManageNetworkScreen(
    workHubViewModel: WorkHubViewModel,
    manageNetworkViewModel: ManageNetworkViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val manageNetworkUiState by manageNetworkViewModel.uiState.collectAsState()
    var state by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        manageNetworkViewModel.getConnections(email = uiState.user)
        manageNetworkViewModel.getFollowedPages(email = uiState.user)
    }

    OnEvent(manageNetworkViewModel.event) {
        when(it) {
            ConnectEvent.RemoveConnection -> {
                workHubViewModel.getLoggedUser()
            }

            PageEvent.UnfollowPageEvent -> {
                workHubViewModel.getLoggedUser()
            }
        }
    }

    Column {
        TabRow(
            selectedTabIndex = state,
            backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE)
        ) {
            Tab(
                text = { Text("CONNECTIONS") },
                selected = state == 0,
                onClick = { state = 0 }
            )

            Tab(
                text = { Text("PAGES") },
                selected = state == 1,
                onClick = { state = 1 }
            )
        }

        if (state == 0) {
            LazyColumn {
                for (connection in manageNetworkUiState.connections) {
                    item {
                        ConnectionCard(
                            connection = connection,
                            curr_user = uiState.curr_user,
                            navController = navController,
                            manageNetworkViewModel = manageNetworkViewModel,
                            workHubViewModel = workHubViewModel
                        )

                        Divider()
                    }
                }
            }
        } else {
            LazyColumn {
                for (page in manageNetworkUiState.followed_pages) {
                    item {
                        FollowedPageCard(
                            page = page,
                            curr_user = uiState.curr_user,
                            navController = navController,
                            manageNetworkViewModel = manageNetworkViewModel,
                            workHubViewModel = workHubViewModel
                        )

                        Divider()
                    }
                }
            }
        }
    }
}