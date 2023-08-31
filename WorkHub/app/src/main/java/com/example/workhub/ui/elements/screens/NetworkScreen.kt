package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workhub.R
import com.example.workhub.SnippetViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NetworkScreen(
    viewModelFromActivity: SnippetViewModel,
    modifier: Modifier = Modifier,
) {
    val viewModelFromRoute: SnippetViewModel = viewModel()

    var value by remember { mutableStateOf("") }
    val uiStateFromRoute by viewModelFromRoute.uiState.collectAsState()
    val uiStateFromActivity by viewModelFromActivity.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier.height(70.dp), backgroundColor = Color(0xFF202020)) {
                Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {}) {
                        Image(
                            painter = painterResource(id = R.drawable.linkedin),
                            contentDescription = "linkedin"
                            ,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(54.dp)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 10.dp)){
                        OutlinedTextField(
                            value = "",
                            placeholder = {
                                Row {
                                    Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                                    Text(text = "Search")
                                }
                            },
                            onValueChange = {}
                        )
                    }

                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier
                                .size(54.dp)
                        )
                    }
                }
            }
        },
    ) {
        LazyColumn {
            item {
                Row(modifier = Modifier.padding(10.dp)) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextButton(onClick = { }) {
                            Text(text = "Manage my network", color = Color(0xFF0077B5))
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextButton(onClick = { }) {
                            Text(text = "Invitations", color = Color(0xFF0077B5))
                        }
                    }
                }
            }

            item {
                Card(backgroundColor = Color(0xFF202020), modifier = Modifier.padding(10.dp)) {
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
                                cells = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                                contentPadding = PaddingValues(all = 10.dp),
                                modifier = Modifier.height(400.dp),

                                ) {
                                items(count = 4) {
                                    Card {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = "jdd",
                                                modifier = Modifier.size(70.dp)
                                            )

                                            Text(text = "Pera Peric", fontSize = 20.sp)

                                            Text(text = "Software Engineer at TomTom", fontSize = 12.sp, textAlign = TextAlign.Center)

                                            Text(text = "20 mutual connections", fontSize = 12.sp)

                                            OutlinedButton(onClick = { /*TODO*/ }) {
                                                Text(text = "Connect", color = Color(0xFF0077B5))
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
                Card(backgroundColor = Color(0xFF202020), modifier = Modifier.padding(10.dp)) {
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
                                cells = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                                contentPadding = PaddingValues(all = 10.dp),
                                modifier = Modifier.height(360.dp)
                            ) {
                                items(count = 4) {
                                    Card {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = "jdd",
                                                modifier = Modifier.size(70.dp)
                                            )

                                            Text(text = "ETF", fontSize = 20.sp)

                                            Text(text = "20 connections follow this page", fontSize = 12.sp)

                                            OutlinedButton(onClick = { /*TODO*/ }) {
                                                Text(text = "Follow", color = Color(0xFF0077B5))
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
                Card(backgroundColor = Color(0xFF202020), modifier = Modifier.padding(10.dp)) {
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
                                cells = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                                contentPadding = PaddingValues(all = 10.dp),
                                modifier = Modifier.height(400.dp)
                            ) {
                                items(count = 4) {
                                    Card {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = "jdd",
                                                modifier = Modifier.size(70.dp)
                                            )

                                            Text(text = "Pera Peric", fontSize = 20.sp)

                                            Text(text = "Software Engineer at TomTom", fontSize = 12.sp, textAlign = TextAlign.Center)

                                            Text(text = "20 mutual connections", fontSize = 12.sp)

                                            OutlinedButton(onClick = { /*TODO*/ }) {
                                                Text(text = "Connect", color = Color(0xFF0077B5))
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
}