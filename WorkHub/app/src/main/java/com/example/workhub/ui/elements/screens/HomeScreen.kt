package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workhub.SnippetViewModel
import com.example.workhub.ui.elements.composables.Post

@Composable
fun HomeScreen(
    viewModelFromActivity: SnippetViewModel
) {
    val viewModelFromRoute: SnippetViewModel = viewModel()

    val uiStateFromRoute by viewModelFromRoute.uiState.collectAsState()
    val uiStateFromActivity by viewModelFromActivity.uiState.collectAsState()

    LazyColumn {
        items(count = 2) {
            Post(last = false)
        }

        item { Post(last = true) }
    }
}