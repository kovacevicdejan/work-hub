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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.workhub.SnippetViewModel
import com.example.workhub.ui.elements.theme.Shapes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ManageNetworkScreen(
    viewModelFromActivity: SnippetViewModel,
    navController: NavHostController
) {
    val viewModelFromRoute: SnippetViewModel = viewModel()

    val uiStateFromRoute by viewModelFromRoute.uiState.collectAsState()
    val uiStateFromActivity by viewModelFromActivity.uiState.collectAsState()

    var state by remember { mutableStateOf(0) }

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

        if(state == 0) {
            LazyColumn {
                for (i in 0..10) {
                    item {
                        Card(
                            backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
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
                                    Text(text = "Petar Petrovic", fontSize = 20.sp)

                                    Text(
                                        text = "Software engineer at Microsoft",
                                        fontSize = 14.sp
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Button(onClick = {}, modifier = Modifier.padding(horizontal = 10.dp))
                                {
                                    Text(text = "Remove", color = Color.White)
                                }
                            }
                        }

                        Divider()
                    }
                }
            }
        }
        else {
            LazyColumn {
                for (i in 0..10) {
                    item {
                        Card(
                            backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
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

                                Button(onClick = {}, modifier = Modifier.padding(horizontal = 10.dp))
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