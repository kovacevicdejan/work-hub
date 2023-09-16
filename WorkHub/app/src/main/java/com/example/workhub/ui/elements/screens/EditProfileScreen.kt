package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EditProfileScreen (
) {
    var jobTitle by rememberSaveable { mutableStateOf("") }

    Card(modifier = Modifier.padding(10.dp), backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE)) {
        Column {
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Edit profile",
                            modifier = Modifier.weight(1f),
                            fontSize = 30.sp
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "First name: ",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        OutlinedTextField(
                            value = jobTitle,
                            onValueChange = { jobTitle = it },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Last Name: ",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        OutlinedTextField(
                            value = jobTitle,
                            onValueChange = { jobTitle = it },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Headline: ",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        OutlinedTextField(
                            value = jobTitle,
                            onValueChange = { jobTitle = it },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                        Text(
                            text = "About: ",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )
                    }

                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Industry: ",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        OutlinedTextField(
                            value = jobTitle,
                            onValueChange = { jobTitle = it },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Location: ",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        OutlinedTextField(
                            value = jobTitle,
                            onValueChange = { jobTitle = it },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Contact Info: ",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        OutlinedTextField(
                            value = jobTitle,
                            onValueChange = { jobTitle = it },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Save", color = Color.White, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}