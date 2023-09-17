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
import com.example.workhub.ui.elements.composables.ProfileImage
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.InvitationsViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InvitationsScreen(
    workHubViewModel: WorkHubViewModel,
    invitationsViewModel: InvitationsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val invitationsUiState by invitationsViewModel.uiState.collectAsState()
    var state by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        uiState.curr_user?.let { invitationsViewModel.getInvitations(it) }
    }

    Column {
        TabRow(
            selectedTabIndex = state,
            backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE)
        ) {
            Tab(
                text = { Text("RECEIVED") },
                selected = state == 0,
                onClick = { state = 0 }
            )

            Tab(
                text = { Text("SENT") },
                selected = state == 1,
                onClick = { state = 1 }
            )
        }

        if(state == 0) {
            LazyColumn {
                for (user in invitationsUiState.received_invitation) {
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
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                ProfileImage(
                                    image_name = user.profile_image,
                                    size = 60,
                                    padding = 5
                                )

                                Column {
                                    Text(text = user.firstname + " " + user.lastname, fontSize = 20.sp)

                                    Text(
                                        text = user.headline,
                                        fontSize = 14.sp
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Button(
                                        onClick = {},
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    )
                                    {
                                        Text(text = "Accept", color = Color.White)
                                    }

                                    Button(onClick = {}, modifier = Modifier.padding(horizontal = 10.dp))
                                    {
                                        Text(text = "Decline", color = Color.White)
                                    }
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
                for (user in invitationsUiState.sent_invitations!!) {
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
                                ProfileImage(
                                    image_name = user.profile_image,
                                    size = 60,
                                    padding = 5
                                )

                                Column {
                                    Text(text = user.firstname + " " + user.lastname, fontSize = 20.sp)

                                    Text(
                                        text = user.headline,
                                        fontSize = 14.sp
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Button(onClick = {}, modifier = Modifier.padding(horizontal = 10.dp))
                                {
                                    Text(text = "Withdraw", color = Color.White)
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