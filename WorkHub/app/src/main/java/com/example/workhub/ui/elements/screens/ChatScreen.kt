package com.example.workhub.ui.elements.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workhub.ui.elements.theme.Shapes

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            Card(
                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            modifier = Modifier
                                .weight(5.6f)
                                .padding(horizontal = 10.dp)
                        )

                        Button(
                            onClick = {

                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        ) {
//                            Text(text = "Send", color = Color.White)
                            Icon(imageVector = Icons.Default.Send, contentDescription = null)
                        }
                    }

                    Divider()
                }
            }
        }
    ) {
        LazyColumn {
            item {
                Card(
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .fillMaxWidth(),
                    backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                    shape = Shapes.large
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        IconButton(
                            onClick = {
                                navController.navigate("Profile") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(60.dp)
                            )
                        }

                        Column {
                            Text(text = "Petar Petrovic", fontSize = 20.sp)

                            Text(text = "Software engineer at Nutanix", fontSize = 14.sp)
                        }
                    }
                }
            }

            for(i in 1..5) {
                item {
                    Card(
                        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                            0xFFEEEEEE
                        ),
                        shape = Shapes.large,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, if(i == 5) 77.dp else 0.dp)
                    ) {
                        Column {
                            Divider()

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "DEC 29, 2022")
                            }

                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 5.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(30.dp)
                                            .height(30.dp)
                                    )

                                    Text(text = "Petar Petrovic | 10:56", fontSize = 20.sp)
                                }

                                Column(modifier = Modifier.padding(30.dp, 0.dp, 0.dp, 0.dp)) {
                                    Text(
                                        text = "I saw you got a new job at my company. Congratulations!",
                                        fontSize = 18.sp,
                                        maxLines = Int.MAX_VALUE
                                    )

                                    Text(
                                        text = "Can we meet for a drink?",
                                        fontSize = 18.sp,
                                        maxLines = Int.MAX_VALUE
                                    )
                                }
                            }

                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 5.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(30.dp)
                                            .height(30.dp)
                                    )

                                    Text(text = "Marko Markovic | 10:58", fontSize = 20.sp)
                                }

                                Column(modifier = Modifier.padding(30.dp, 0.dp, 0.dp, 0.dp)) {
                                    Text(
                                        text = "That would be great!",
                                        fontSize = 18.sp,
                                        maxLines = Int.MAX_VALUE
                                    )

                                    Text(
                                        text = "What time works the best for you? I am free at 10:00.",
                                        fontSize = 18.sp,
                                        maxLines = Int.MAX_VALUE
                                    )
                                }
                            }

                            if(i == 5)
                                Divider()
                        }
                    }
                }
            }
        }
    }
}