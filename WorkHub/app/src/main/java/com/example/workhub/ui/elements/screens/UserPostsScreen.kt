package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workhub.ui.elements.composables.Post

@Composable
fun UserPostsScreen(
    navController: NavHostController
) {
    LazyColumn {
        item {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Posts",
                    modifier = Modifier.weight(1f),
                    fontSize = 30.sp
                )
            }
        }

        items(count = 2) {
            Post(last = false, navController = navController)
        }

        item { Post(last = true, navController = navController) }
    }
}