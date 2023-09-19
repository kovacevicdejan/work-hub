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
import com.example.workhub.ui.stateholders.*


@Composable
fun AddExperienceScreen(
    workHubViewModel: WorkHubViewModel,
    addExperienceViewModel: AddExperienceViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val addExperienceUiState by addExperienceViewModel.uiState.collectAsState()

    OnEvent(addExperienceViewModel.event) {
        if (it == EditProfileEvent.AddExperienceEvent) {
            workHubViewModel.getLoggedUser()

            navController.navigate("Profile") {
                launchSingleTop = true
                restoreState = true
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
                            text = "Add experience",
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
                            value = addExperienceUiState.company,
                            onValueChange = { addExperienceViewModel.setCompany(it) },
                            label = { Text(text = "Company") },
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
                            value = addExperienceUiState.job_title,
                            onValueChange = { addExperienceViewModel.setJobTitle(it) },
                            label = { Text(text = "Job title") },
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
                            value = addExperienceUiState.job_type,
                            onValueChange = { addExperienceViewModel.setJobType(it) },
                            label = { Text(text = "Job type") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                        OutlinedTextField(
                            value = addExperienceUiState.start_date,
                            onValueChange = { addExperienceViewModel.setStartDate(it) },
                            label = { Text(text = "Start date") },
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
                            value = addExperienceUiState.end_date,
                            onValueChange = { addExperienceViewModel.setEndDate(it) },
                            label = { Text(text = "End date") },
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
                            value = addExperienceUiState.location,
                            onValueChange = { addExperienceViewModel.setLocation(it) },
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
                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                uiState.curr_user?.let { addExperienceViewModel.addExperience(it.email) }
                            }
                        ) {
                            Text(text = "Add", color = Color.White, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}