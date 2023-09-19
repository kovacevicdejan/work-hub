package com.example.workhub.ui.elements.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.workhub.HomeDestination
import com.example.workhub.data.retrofit.BASE_URL
import com.example.workhub.ui.elements.composables.ProfileImage
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.WorkHubViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen (
    workHubViewModel: WorkHubViewModel,
    navController: NavHostController
) {
    // about, education, experience, skills, languages, contact info
    val uiState by workHubViewModel.uiState.collectAsState()

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
                       ProfileImage(
                           image_name = uiState.curr_user?.profile_image ?: "",
                           size = 120,
                           vertical_padding = 10,
                           horizontal_padding = 10
                       )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = (uiState.curr_user?.firstname ?: "") + " " + (uiState.curr_user?.lastname
                                    ?: ""),
                                fontSize = 30.sp,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            Text(
                                text = uiState.curr_user?.headline ?: "",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            Text(
                                text = uiState.curr_user?.location ?: "",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
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

                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    TextButton(
                                        onClick = {
                                            navController.navigate("Manage Network") {
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        modifier = Modifier.padding(horizontal = 2.dp)
                                    ) {
                                        Text(
                                            text = "${uiState.curr_user?.connections?.size} connections",
                                            color = Color(0xFF0077B5)
                                        )
                                    }
                                }

                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Button(
                                        onClick = {
                                            navController.navigate("Sign In") {
                                                launchSingleTop = true
                                                restoreState = true
                                                popUpTo(HomeDestination.route) {
                                                    saveState = false
                                                    inclusive = false
                                                }
                                            }

                                            workHubViewModel.signOut()
                                        },
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp)
                                    ) {
                                        Text(text = "Sign Out", color = Color.White)
                                    }
                                }
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
                                onClick = {
                                    workHubViewModel.setUser(uiState.curr_user?.email ?: "")

                                    navController.navigate("User Posts") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
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
                                    navController.navigate("Create Page") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                            ) {
                                Text(text = "Create page", color = Color.White)
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
                            text = uiState.curr_user?.about ?: "",
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
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Text(text = "Experience", fontSize = 20.sp)

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(
                            onClick = {
                                navController.navigate("Add Experience") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        ) {
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

                    if(uiState.curr_user != null) {
                        for(i in uiState.curr_user!!.experience.size - 1 downTo 0) {
                            val exp = uiState.curr_user!!.experience[i]

                            Row(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp)) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(70.dp)
                                        .height(70.dp)
                                )

                                Column {
                                    Text(text = exp.job_title, fontSize = 16.sp, modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp))

                                    Text(text = "${exp.company} | ${exp.job_type}", fontSize = 14.sp)

                                    Text(text = "${exp.start_date} - ${exp.end_date}", fontSize = 14.sp)

                                    Text(text = exp.location, fontSize = 14.sp)
                                }
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
                        Text(text = "Skills", fontSize = 20.sp)
                    }

                    Row(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp)) {
                        Column {
                            if(uiState.curr_user != null) {
                                for (skill in uiState.curr_user!!.skills) {
                                    Text(
                                        text = skill.name,
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(vertical = 5.dp)
                                    )

                                    Divider()
                                }
                            }

                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = uiState.skill,
                                    onValueChange = { workHubViewModel.setSkill(it) },
                                    modifier = Modifier
                                        .weight(2.4f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                        .weight(3f)
                                )

                                Button(
                                    onClick = {
                                        workHubViewModel.addSkill()
                                        workHubViewModel.setSkill("")
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = "Add skill", color = Color.White)
                                }
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
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Text(text = "Contact info", fontSize = 20.sp)
                    }

                    Row(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp)) {
                        Column {
                            Text(text = "Email: ${uiState.curr_user!!.email}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 5.dp))

                            Text(text = "Phone: ${uiState.curr_user!!.phone_number}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 5.dp))
                        }
                    }
                }
            }
        }
    }
}