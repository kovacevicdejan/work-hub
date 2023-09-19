package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.workhub.ui.elements.composables.Post

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)) {
//        LazyColumn {
//            items(count = 3) {
//                Post(navController = navController)
//            }
//        }
    }
}