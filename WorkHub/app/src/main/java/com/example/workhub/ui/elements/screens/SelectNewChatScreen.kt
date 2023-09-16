package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
@Composable
fun SelectNewChatScreen(
    navController: NavHostController
) {
    LazyColumn {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
            ) {
                Text(text = "Select connection for new chat", fontSize = 20.sp)
            }
        }

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
                    },
                    modifier = Modifier.fillMaxWidth()
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
                                text = "Software engineer at Microsoft",
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Divider()
            }
        }
    }
}