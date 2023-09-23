package com.example.workhub.ui.elements.screens

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.workhub.HomeDestination
import com.example.workhub.data.retrofit.models.Applicant
import com.example.workhub.ui.elements.composables.PageImage
import com.example.workhub.ui.elements.composables.ProfileImage
import com.example.workhub.ui.elements.composables.SearchUserCard
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.JobEvent
import com.example.workhub.ui.stateholders.JobViewModel
import com.example.workhub.ui.stateholders.OnEvent
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun JobScreen(
    workHubViewModel: WorkHubViewModel,
    jobViewModel: JobViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val jobUiState by jobViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        jobViewModel.getJob(uiState.job_id)
    }

    OnEvent(jobViewModel.event) {
        if (it == JobEvent.ApplyForJob) {
            navController.navigate(HomeDestination.route) {
                launchSingleTop = true
                restoreState = true
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
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Column {
                            Text(text = jobUiState.job?.title ?: "", fontSize = 24.sp)
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        if((jobUiState.page?.admin ?: "") != (uiState.curr_user?.email ?: "")) {
                            if (jobUiState.job != null) {
                                if (!jobUiState.job!!.applicants.contains(
                                        Applicant(
                                            user = uiState.curr_user?.email ?: ""
                                        )
                                    )
                                ) {
                                    Column {
                                        Button(
                                            onClick = {
                                                jobViewModel.applyForJob(
                                                    user = uiState.curr_user?.email ?: "",
                                                    job_id = jobUiState.job?._id ?: ""
                                                )
                                            },
                                            modifier = Modifier
                                                .padding(horizontal = 10.dp)
                                        ) {
                                            Text(text = "Apply", color = Color.White)
                                        }
                                    }
                                } else {
                                    Text(
                                        text = "Applied",
                                        color = Blue,
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                            }
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = {
                                workHubViewModel.setPage(jobUiState.job?.page ?: "")

                                navController.navigate("Page") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        ) {
                            PageImage(image_name = jobUiState.page?.profile_image ?: "", size = 70)
                        }

                        Column {
                            Text(text = jobUiState.job?.page ?: "", fontSize = 20.sp)

                            Text(
                                text = "${jobUiState.job?.location ?: ""} (${jobUiState.job?.workplace_type ?: ""})",
                                fontSize = 12.sp
                            )

                            Text(
                                text = "${jobUiState.job?.applicants?.size ?: 0} applicants",
                                fontSize = 12.sp
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Work,
                            contentDescription = "Work",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )

                        Text(
                            text = "${if ((jobUiState.job?.job_type ?: "") == 0) "Internship" else "Full-time"} | ${jobUiState.job?.level ?: ""}",
                            fontSize = 18.sp
                        )
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Apartment,
                            contentDescription = "Area",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )

                        Text(text = jobUiState.job?.area ?: "", fontSize = 18.sp)
                    }
//
//                    Row(
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Group,
//                            contentDescription = "Group",
//                            modifier = Modifier
//                                .size(30.dp)
//                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
//                        )
//
//                        Column {
//                            Text(text = "5 connections work here", fontSize = 18.sp)
//
//                            Text(text = "10 company alumni work here", fontSize = 18.sp)
//                        }
//                    }
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
                        Text(text = "Job description", fontSize = 20.sp)
                    }

                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text(
                            text = jobUiState.job?.description ?: "",
                            modifier = Modifier.padding(vertical = 10.dp),
                            fontSize = 16.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
                    ) {
                        Text(
                            text = "Tech stack: ${jobUiState.job?.tech_stack ?: 0}",
                            fontSize = 16.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
                    ) {
                        Text(
                            text = "Deadline: ${jobViewModel.getDay()}." +
                                    "${jobViewModel.getMonth()}." +
                                    "${jobViewModel.getYear()}.",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        if ((jobUiState.page?.admin ?: "") == (uiState.curr_user?.email ?: "")) {
            item {
                Card(
                    backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                        0xFFEEEEEE
                    ),
                    shape = Shapes.large,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = "Applicants", fontSize = 24.sp)
                    }
                }

                Divider()
            }

            item {
                for (applicant in jobUiState.applicants) {
                    SearchUserCard(user = applicant, navController = navController, workHubViewModel = workHubViewModel)
                }
            }
        }
    }
}