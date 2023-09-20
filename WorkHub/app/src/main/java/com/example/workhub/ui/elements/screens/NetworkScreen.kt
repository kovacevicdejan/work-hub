package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.workhub.data.retrofit.models.Invitation
import com.example.workhub.ui.elements.composables.ProfileImage
import com.example.workhub.ui.elements.composables.RecommendedPage
import com.example.workhub.ui.elements.composables.RecommendedUser
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.stateholders.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NetworkScreen(
    workHubViewModel: WorkHubViewModel,
    networkViewModel: NetworkViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val networkUiState by networkViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        networkViewModel.getRecommendedUsers(
            email = uiState.curr_user?.email ?: ""
        )

        networkViewModel.getRecommendedPages(
            email = uiState.curr_user?.email ?: ""
        )
    }

    OnEvent(networkViewModel.event) {
        when(it) {
            ConnectEvent.ConnectSuccess -> {
                workHubViewModel.getLoggedUser()
            }

            PageEvent.FollowPageEvent -> {
                workHubViewModel.getLoggedUser()
            }
        }
    }

    LazyColumn {
        item {
            Row(modifier = Modifier.padding(5.dp, 10.dp, 5.dp, 0.dp)) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(
                        onClick = {
                            uiState.curr_user?.let { workHubViewModel.setUser(it.email) }

                            navController.navigate("Manage Network") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        Text(text = "Manage my network", color = Color(0xFF0077B5))
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(
                        onClick = {
                            navController.navigate("Invitations") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        Text(text = "Invitations", color = Color(0xFF0077B5))
                    }
                }
            }
        }

        item {
            Card(
                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF1a1a1a)
                else Color(0xFFEEEEEE),
                modifier = Modifier.padding(10.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(text = "Recommended users to connect with")
                    }

                    Row {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                            contentPadding = PaddingValues(all = 10.dp),
                            modifier = Modifier.height(400.dp)
                        ) {
                            for (user in networkUiState.users!!) {
                                item {
                                    uiState.curr_user?.let {
                                        RecommendedUser(
                                            user1 = it,
                                            user2 = user,
                                            navController = navController,
                                            networkViewModel = networkViewModel,
                                            workHubViewModel = workHubViewModel
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Card(
                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF1a1a1a)
                else Color(0xFFEEEEEE),
                modifier = Modifier.padding(10.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(text = "Recommended pages to follow")
                    }

                    Row {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                            contentPadding = PaddingValues(all = 10.dp),
                            modifier = Modifier.height(360.dp)
                        ) {
                            for (page in networkUiState.pages!!) {
                                item {
                                    uiState.curr_user?.let {
                                        RecommendedPage(
                                            user = it,
                                            page = page,
                                            navController = navController,
                                            networkViewModel = networkViewModel,
                                            workHubViewModel = workHubViewModel
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}