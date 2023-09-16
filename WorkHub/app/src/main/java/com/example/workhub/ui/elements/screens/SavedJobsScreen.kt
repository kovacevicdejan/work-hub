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
fun SavedJobsScreen(
    navController: NavHostController
) {
    Column {
        LazyColumn {
            item {
                Card(
                    backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                    shape = Shapes.large,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = "Saved jobs", fontSize = 24.sp)
                    }
                }

                Divider()
            }

            for(i in 0..7) {
                item {
                    Card(
                        backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                        shape = Shapes.large,
                        onClick = {
                            navController.navigate("Job Post") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp)
                            )

                            Column {
                                Text(text = "Senior Software engineer", fontSize = 20.sp)

                                Text(text = "TomTom", fontSize = 14.sp)

                                Text(text = "Belgrade, Serbia", fontSize = 14.sp)

                                Text(text = "Hybrid", fontSize = 14.sp)
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Button(
                                onClick = {},
                                modifier = Modifier.padding(horizontal = 10.dp)
                            ) {
                                Text(text = "Remove", color = Color.White)
                            }
                        }
                    }

                    Divider()
                }
            }
        }
    }
}