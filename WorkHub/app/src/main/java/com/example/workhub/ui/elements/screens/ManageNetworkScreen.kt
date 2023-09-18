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
import com.example.workhub.ui.elements.composables.ProfileImage
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.ConnectEvent
import com.example.workhub.ui.stateholders.ManageNetworkViewModel
import com.example.workhub.ui.stateholders.OnEvent
import com.example.workhub.ui.stateholders.WorkHubViewModel

@OptIn(ExperimentalMaterialApi::class)
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
        uiState.curr_user?.let { manageNetworkViewModel.getConnections(curr_user = it) }
    }

    OnEvent(manageNetworkViewModel.event) {
        if(it == ConnectEvent.RemoveConnection) {
            workHubViewModel.getLoggedUser()
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
                            manageNetworkViewModel = manageNetworkViewModel
                        )

                        Divider()
                    }
                }
            }
        } else {
            LazyColumn {
                for (i in 0..10) {
                    item {
                        Card(
                            backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                                0xFFEEEEEE
                            ),
                            shape = Shapes.large,
                            onClick = {
                                navController.navigate("Profile") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(60.dp)
                                )

                                Column {
                                    Text(text = "TomTom", fontSize = 20.sp)

                                    Text(
                                        text = "200000 followers",
                                        fontSize = 14.sp
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Button(
                                    onClick = {},
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                                {
                                    Text(text = "Unfollow", color = Color.White)
                                }
                            }
                        }

                        Divider()
                    }
                }
            }
        }
    }
}