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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.workhub.HomeDestination
import com.example.workhub.data.retrofit.BASE_URL
import com.example.workhub.data.retrofit.models.Connection
import com.example.workhub.data.retrofit.models.Invitation
import com.example.workhub.ui.elements.composables.ProfileImage
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    workHubViewModel: WorkHubViewModel,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {
    // about, education, experience, skills, languages, contact info
    val uiState by workHubViewModel.uiState.collectAsState()
    val profileUiState by profileViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.getUser(uiState.user)
    }

    OnEvent(profileViewModel.event) {
        when(it) {
            ConnectEvent.ConnectSuccess -> {
                workHubViewModel.getLoggedUser()
            }
        }
    }

    LazyColumn {
        item {
            Card(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if(profileUiState.user == uiState.curr_user) {
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

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ProfileImage(
                                image_name = profileUiState.user?.profile_image ?: "",
                                size = 120,
                                vertical_padding = 10,
                                horizontal_padding = 10
                            )
                        }

                        if(profileUiState.user == uiState.curr_user) {
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

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = (profileUiState.user?.firstname
                                    ?: "") + " " + (profileUiState.user?.lastname
                                    ?: ""),
                                fontSize = 30.sp,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            Text(
                                text = profileUiState.user?.headline ?: "",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            Text(
                                text = profileUiState.user?.location ?: "",
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
                                            text = "${profileUiState.user?.connections?.size ?: 0} connections",
                                            color = Color(0xFF0077B5)
                                        )
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

                        if(profileUiState.user == uiState.curr_user) {
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
                        else {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if(profileUiState.user?.received_invitations?.contains(Invitation(
                                        user = uiState.curr_user?.email ?: ""
                                    )) == true) {
                                    Text(
                                        text = "Pending",
                                        color = Blue,
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                                else if(profileUiState.user?.sent_invitations?.contains(Invitation(
                                        user = uiState.curr_user?.email ?: ""
                                    )) == true) {
                                    Text(
                                        text = "Pending",
                                        color = Blue,
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                                else if(profileUiState.user?.connections?.contains(Connection(
                                        user = uiState.curr_user?.email ?: ""
                                    )) == true) {
                                    Text(
                                        text = "Connected",
                                        color = Blue,
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                                else {
                                    Button(
                                        onClick = {
                                            profileViewModel.connect(
                                                user1 = uiState.curr_user?.email ?: "",
                                                user2 = profileUiState.user?.email ?: ""
                                            )
                                        },
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp)
                                    ) {
                                        Text(text = "Connect", color = Color.White)
                                    }
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
                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
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
                            text = profileUiState.user?.about ?: "",
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
                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Text(text = "Experience", fontSize = 20.sp)

                        Spacer(modifier = Modifier.weight(1f))

                        if(profileUiState.user == uiState.curr_user) {
                            IconButton(
                                onClick = {
                                    navController.navigate("Add Experience") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add",
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }

                            IconButton(onClick = { /*TODO*/ }) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                        }
                    }

                    if (profileUiState.user != null) {
                        for (i in profileUiState.user!!.experience.size - 1 downTo 0) {
                            val exp = profileUiState.user!!.experience[i]

                            Row(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp)) {
                                Column {
                                    Text(
                                        text = exp.job_title,
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)
                                    )

                                    Text(
                                        text = "${exp.company} | ${exp.job_type}",
                                        fontSize = 14.sp
                                    )

                                    Text(
                                        text = "${exp.start_date} - ${exp.end_date}",
                                        fontSize = 14.sp
                                    )

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
                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
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
                            if (profileUiState.user != null) {
                                for (skill in profileUiState.user!!.skills) {
                                    Text(
                                        text = skill.name,
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(vertical = 5.dp)
                                    )

                                    Divider()
                                }
                            }

                            if(profileUiState.user == uiState.curr_user) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 10.dp
                                    ),
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
        }

        item {
            Card(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    .fillMaxWidth(),
                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)
                    ) {
                        Text(text = "Contact info", fontSize = 20.sp)
                    }

                    Row(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp)) {
                        Column {
                            Text(
                                text = "Email: ${profileUiState.user?.email ?: ""}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )

                            Text(
                                text = "Phone: ${profileUiState.user?.phone_number ?: ""}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}