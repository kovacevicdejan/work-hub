package com.example.workhub.ui.elements.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workhub.ui.elements.theme.Shapes

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatsScreen(
    navController: NavHostController
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("Select New Chat") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.AddComment, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp),
        ) {
            for (i in 0..10) {
                item {
                    Card(
                        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                        shape = Shapes.large,
                        onClick = {
                            navController.navigate("Single Chat") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp)
                            )

                            Column {
                                Text(text = "Petar Petrovic", fontSize = 20.sp)

                                Text(
                                    text = "Thank you! It means a lot to me.",
                                    fontSize = 14.sp
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = "27.8.2023.",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )
                        }
                    }

                    Divider()
                }
            }
        }
    }
}