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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NetworkScreen(
    navController: NavHostController
) {
    LazyColumn {
        item {
            Row(modifier = Modifier.padding(5.dp, 10.dp, 5.dp, 0.dp)) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(
                        onClick = {
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
                backgroundColor = if(isSystemInDarkTheme()) Color(0xFF1a1a1a)
                else Color(0xFFEEEEEE),
                modifier = Modifier.padding(10.dp)) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(text = "People you may know from University of Belgrade")
                    }

                    Row {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                            contentPadding = PaddingValues(all = 10.dp),
                            modifier = Modifier.height(400.dp),

                            ) {
                            items(count = 4) {
                                Card(
                                    onClick = {
                                        navController.navigate("Profile") {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "jdd",
                                            modifier = Modifier.size(70.dp)
                                        )

                                        Text(text = "Pera Peric", fontSize = 20.sp)

                                        Text(text = "Software Engineer at TomTom", fontSize = 12.sp, textAlign = TextAlign.Center)

                                        Text(text = "20 mutual connections", fontSize = 12.sp)

                                        Button(onClick = { /*TODO*/ }) {
                                            Text(text = "Connect", color = Color.White)
                                        }
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
                backgroundColor = if(isSystemInDarkTheme()) Color(0xFF1a1a1a)
                else Color(0xFFEEEEEE),
                modifier = Modifier.padding(10.dp)) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(text = "Popular pages across Linkedin")
                    }

                    Row {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                            contentPadding = PaddingValues(all = 10.dp),
                            modifier = Modifier.height(360.dp)
                        ) {
                            items(count = 4) {
                                Card(
                                    onClick = {
                                        navController.navigate("Page") {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "jdd",
                                            modifier = Modifier.size(70.dp)
                                        )

                                        Text(text = "TomTom", fontSize = 20.sp)

                                        Text(text = "20 connections follow this page", fontSize = 12.sp)

                                        Button(onClick = { /*TODO*/ }) {
                                            Text(text = "Follow", color = Color.White)
                                        }
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
                backgroundColor = if(isSystemInDarkTheme()) Color(0xFF1a1a1a)
                else Color(0xFFEEEEEE),
                modifier = Modifier.padding(10.dp)) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(text = "More suggestions")
                    }

                    Row {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                            contentPadding = PaddingValues(all = 10.dp),
                            modifier = Modifier.height(400.dp)
                        ) {
                            items(count = 4) {
                                Card(
                                    onClick = {
                                        navController.navigate("Profile") {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "jdd",
                                            modifier = Modifier.size(70.dp)
                                        )

                                        Text(text = "Pera Peric", fontSize = 20.sp)

                                        Text(text = "Software Engineer at TomTom", fontSize = 12.sp, textAlign = TextAlign.Center)

                                        Text(text = "20 mutual connections", fontSize = 12.sp)

                                        Button(onClick = { /*TODO*/ }) {
                                            Text(text = "Connect", color = Color.White)
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
}