package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.workhub.R
import com.example.workhub.SnippetViewModel
import com.example.workhub.ui.elements.composables.Post
import com.example.workhub.ui.elements.theme.Shapes

@Composable
fun JobScreen(
    viewModelFromActivity: SnippetViewModel,
    navController: NavHostController
) {
    val viewModelFromRoute: SnippetViewModel = viewModel()

    val uiStateFromRoute by viewModelFromRoute.uiState.collectAsState()
    val uiStateFromActivity by viewModelFromActivity.uiState.collectAsState()

    LazyColumn() {
        item {
            Card(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = "Software engineer intern at TomTom", fontSize = 24.sp)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .width(70.dp)
                                .height(70.dp)
                        )

                        Column {
                            Text(text = "TomTom", fontSize = 20.sp)

                            Text(text = "Belgrade, Serbia (Hybrid)", fontSize = 12.sp)

                            Text(text = "50 applicants", fontSize = 12.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Work,
                            contentDescription = "Work",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )

                        Text(text = "Full-time | Entry level", fontSize = 18.sp)
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Apartment,
                            contentDescription = "Industry",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )

                        Text(text = "Software Engineering", fontSize = 18.sp)
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Group,
                            contentDescription = "Group",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )

                        Column() {
                            Text(text = "5 connections work here", fontSize = 18.sp)

                            Text(text = "30 school alumni work here", fontSize = 18.sp)

                            Text(text = "10 company alumni work here", fontSize = 18.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {},
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                            ) {
                                Text(text = "Apply", color = Color.White)
                            }
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {},
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                            ) {
                                Text(text = "Save", color = Color.White)
                            }
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    .fillMaxWidth(),
                backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)
                    ) {
                        Text(text = "Job description", fontSize = 20.sp)
                    }

                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text(
                            text = "Below doc contains all useful shortcuts for quick navigation in VS Code and also extensions which will increase your productivity as well as beautify your code.\n" +
                                    "\n" +
                                    "Save this pdf and Repost if you find this helpful.\n" +
                                    "\n" +
                                    "Learn programming from W3Schools.com\n" +
                                    "\n" +
                                    "Follow Ajit Kumar Gupta for more.\n" +
                                    "\n" +
                                    "Credit - JSMastery , Maheshpal.",
                            modifier = Modifier.padding(vertical = 10.dp),
                            fontSize = 16.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
                    ) {
                        Text(text = "Deadline: 23.8.2023.", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}