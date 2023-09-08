package com.example.workhub.ui.elements.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workhub.R
import com.example.workhub.SnippetViewModel
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.elements.theme.Shapes

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen (
    viewModelFromActivity: SnippetViewModel,
    navController: NavHostController
) {
    // about, education, experience, skills, languages, contact info

    LazyColumn {
        item {
            Card(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Dejan Kovacevic",
                                fontSize = 30.sp,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            Text(
                                text = "Software Engineering Student",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            Text(
                                text = "University of Belgrade",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(12.dp, 10.dp, 0.dp, 0.dp)
                            )

                            Text(
                                text = "Belgrade, Serbia",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            TextButton(
                                onClick = {
                                    navController.navigate("Manage Network") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                modifier = Modifier.padding(horizontal = 2.dp)
                            ) {
                                Text(text = "225 connections", color = Color(0xFF0077B5))
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {},
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                            ) {
                                Text(text = "View posts", color = Color.White)
                            }
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate("Edit Profile") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                            ) {
                                Text(text = "Edit profile", color = Color.White)
                            }
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    .fillMaxWidth(),
                backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)
                    ) {
                        Text(text = "About", fontSize = 20.sp)
                    }

                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text(
                            text = "Below doc contains all useful shortcuts for quick navigation in VS Code and also extensions which will increase your productivity as well as beautify your code.\n" +
                                    "\n" +
                                    "Save this pdf and Repost if you find this helpful.\n" +
                                    "\n" +
                                    "Learn programming from W3Schools.com\n" +
                                    "\n" +
                                    "Follow Ajit Kumar Gupta for more.\n" +
                                    "\n" +
                                    "Credit - JSMastery , Maheshpal.",
                            modifier = Modifier.padding(vertical = 10.dp),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    .fillMaxWidth(),
                backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp)
                    ) {
                        Text(text = "Experience", fontSize = 20.sp)

                        Spacer(modifier = Modifier.weight(1f));

                        IconButton(onClick = { /*TODO*/ }) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(30.dp))
                            }
                        }

                        IconButton(onClick = { /*TODO*/ }) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(30.dp))
                            }
                        }
                    }

                    Row(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp)) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .width(70.dp)
                                .height(70.dp)
                        )

                        Column {
                            Text(text = "Microsoft", fontSize = 20.sp, modifier = Modifier.padding(vertical = 5.dp))

                            Text(text = "6 years", fontSize = 16.sp, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp))

                            Text(text = "Software Engineer Intern", fontSize = 16.sp)

                            Text(text = "Internship", fontSize = 14.sp)

                            Text(text = "Nov 2022 - Feb 2023 | 4 months", fontSize = 14.sp)

                            Text(text = "Belgrade, Serbia", fontSize = 14.sp, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp))

                            Text(text = "Software Engineer Intern", fontSize = 16.sp)

                            Text(text = "Internship", fontSize = 14.sp)

                            Text(text = "Nov 2022 - Feb 2023 | 4 months", fontSize = 14.sp)

                            Text(text = "Belgrade, Serbia", fontSize = 14.sp)
                        }
                    }

                    Row(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp)) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .width(70.dp)
                                .height(70.dp)
                        )

                        Column {
                            Text(text = "Software Engineer Intern", fontSize = 16.sp, modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp))

                            Text(text = "Microsoft | Internship", fontSize = 14.sp)

                            Text(text = "Nov 2022 - Feb 2023 | 4 months", fontSize = 14.sp)

                            Text(text = "Belgrade, Serbia", fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    .fillMaxWidth(),
                backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Text(text = "Education", fontSize = 20.sp)


                        Spacer(modifier = Modifier.weight(1f));

                        IconButton(onClick = { /*TODO*/ }) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(30.dp))
                            }
                        }

                        IconButton(onClick = { /*TODO*/ }) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(30.dp))
                            }
                        }
                    }

                    Row(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp)) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .width(70.dp)
                                .height(70.dp)
                        )

                        Column {
                            Text(text = "University of Belgrade", fontSize = 16.sp, modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp))

                            Text(text = "Bachelors of Science - BS, Computer Science", fontSize = 14.sp)

                            Text(text = "Engineering", fontSize = 14.sp)

                            Text(text = "2019 - 2023", fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    .fillMaxWidth(),
                backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Text(text = "Skills", fontSize = 20.sp)


                        Spacer(modifier = Modifier.weight(1f));

                        IconButton(onClick = { /*TODO*/ }) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(30.dp))
                            }
                        }

                        IconButton(onClick = { /*TODO*/ }) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(30.dp))
                            }
                        }
                    }

                    Row(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp)) {
                        Column {
                            Text(text = "Android", fontSize = 16.sp, modifier = Modifier.padding(vertical = 5.dp))

                            Divider()

                            Text(text = "Kotlin", fontSize = 16.sp, modifier = Modifier.padding(vertical = 5.dp))

                            Divider()

                            Text(text = "Jetpack Compose", fontSize = 16.sp, modifier = Modifier.padding(vertical = 5.dp))

                            Divider()

                            Text(text = "socket.io", fontSize = 16.sp, modifier = Modifier.padding(vertical = 5.dp))
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    .fillMaxWidth(),
                backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Text(text = "Contact info", fontSize = 20.sp)


                        Spacer(modifier = Modifier.weight(1f));

                        IconButton(onClick = { /*TODO*/ }) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(30.dp))
                            }
                        }
                    }

                    Row(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp)) {
                        Column {
                            Text(text = "Email: dejankovacevic10@gmail.com", fontSize = 16.sp, modifier = Modifier.padding(vertical = 5.dp))

                            Text(text = "Phone: +381694625077", fontSize = 16.sp, modifier = Modifier.padding(vertical = 5.dp))
                        }
                    }
                }
            }
        }
    }
}