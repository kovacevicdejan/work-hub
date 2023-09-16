package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.workhub.ui.elements.composables.Post

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    LazyColumn {
        items(count = 2) {
            Post(last = false, navController = navController)
        }

        item { Post(last = true, navController = navController) }
    }
}