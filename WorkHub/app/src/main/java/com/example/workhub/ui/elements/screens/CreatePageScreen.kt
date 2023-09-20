package com.example.workhub.ui.elements.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.workhub.ui.stateholders.CreatePageViewModel
import com.example.workhub.ui.stateholders.OnEvent
import com.example.workhub.ui.stateholders.PageEvent
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun CreatePageScreen(
    workHubViewModel: WorkHubViewModel,
    createPageViewModel: CreatePageViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val createPageUiState by createPageViewModel.uiState.collectAsState()
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {
            it?.let { uri ->
                createPageViewModel.setImageUri(image_uri = uri)
            }
        }

    OnEvent(createPageViewModel.event) {
        if (it == PageEvent.CreatePageEvent) {
            workHubViewModel.setPage(createPageUiState.name)

            navController.navigate("Page") {
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
                            text = "Create page",
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
                            value = createPageUiState.name,
                            onValueChange = { createPageViewModel.setName(it) },
                            label = { Text("Name") },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = createPageUiState.headline,
                            onValueChange = { createPageViewModel.setHeadline(it) },
                            label = { Text("Headline") },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = createPageUiState.about,
                            onValueChange = { createPageViewModel.setAbout(it) },
                            label = { Text("About") },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Button(
                                onClick = {
                                    launcher.launch(arrayOf("image/*"))
                                }
                            ) {
                                Text(text = "Pick Image", color = Color.White)
                            }
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            AsyncImage(
                                model = createPageUiState.image_uri,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                item {
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                        OutlinedTextField(
                            value = createPageUiState.specialties,
                            onValueChange = { createPageViewModel.setSpecialties(it) },
                            label = { Text("Industry") },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                        OutlinedTextField(
                            value = createPageUiState.website,
                            onValueChange = { createPageViewModel.setWebsite(it) },
                            label = { Text("Website") },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = createPageUiState.location,
                            onValueChange = { createPageViewModel.setLocation(it) },
                            label = { Text("Location") },
                            modifier = Modifier.weight(2f)
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
                                uiState.curr_user?.let { createPageViewModel.createPage(context = context, it.email) }
                            }
                        ) {
                            Text(text = "Create", color = Color.White, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}