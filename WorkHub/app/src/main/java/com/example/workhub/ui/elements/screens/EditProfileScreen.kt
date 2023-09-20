package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.workhub.ui.stateholders.EditProfileEvent
import com.example.workhub.ui.stateholders.EditProfileViewModel
import com.example.workhub.ui.stateholders.OnEvent
import com.example.workhub.ui.stateholders.WorkHubViewModel


@Composable
fun EditProfileScreen(
    workHubViewModel: WorkHubViewModel,
    editProfileViewModel: EditProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val editProfileUiState by editProfileViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        uiState.curr_user?.let {
            editProfileViewModel.setFirstname(it.firstname)
            editProfileViewModel.setLastname(it.lastname)
            editProfileViewModel.setAbout(it.about)
            editProfileViewModel.setHeadline(it.headline)
            editProfileViewModel.setPhoneNumber(it.phone_number)
            editProfileViewModel.setLocation(it.location)
            editProfileViewModel.setInterests(it.interests)
        }
    }

    OnEvent(editProfileViewModel.event) {
        when (it) {
            EditProfileEvent.EditProfileSuccess -> {
                workHubViewModel.getLoggedUser()

                navController.navigate("Profile") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }

    Card(
        modifier = Modifier.padding(10.dp),
        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE)
    ) {
        Column {
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Edit profile",
                            modifier = Modifier.weight(1f),
                            fontSize = 30.sp
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = editProfileUiState.firstname,
                            onValueChange = { editProfileViewModel.setFirstname(it) },
                            label = { Text(text = "Firstname") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = editProfileUiState.lastname,
                            onValueChange = { editProfileViewModel.setLastname(it) },
                            label = { Text(text = "Lastname") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = editProfileUiState.headline,
                            onValueChange = { editProfileViewModel.setHeadline(it) },
                            label = { Text(text = "Headline") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                        OutlinedTextField(
                            value = editProfileUiState.about,
                            onValueChange = { editProfileViewModel.setAbout(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            label = { Text(text = "About") },
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = editProfileUiState.location,
                            onValueChange = { editProfileViewModel.setLocation(it) },
                            label = { Text(text = "Location") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = editProfileUiState.interests,
                            onValueChange = { editProfileViewModel.setInterests(it) },
                            label = { Text(text = "Interests") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = editProfileUiState.phone_number,
                            onValueChange = { editProfileViewModel.setPhoneNumber(it) },
                            label = { Text(text = "Phone number") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                uiState.curr_user?.let { editProfileViewModel.editProfile(it.email) }
                            }
                        ) {
                            Text(text = "Save", color = Color.White, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}