package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workhub.SnippetViewModel
import com.example.workhub.ui.elements.composables.Post
import com.example.workhub.R

@Composable
fun HomeScreen(
    viewModelFromActivity: SnippetViewModel,
    modifier: Modifier = Modifier,
) {
    val viewModelFromRoute: SnippetViewModel = viewModel()

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
            items(count = 2) {
                Post(last = false)
            }

            item { Post(last = true) }
        }
    }
}