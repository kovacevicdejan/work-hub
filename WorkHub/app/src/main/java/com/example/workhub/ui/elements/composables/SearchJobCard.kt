package com.example.workhub.ui.elements.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workhub.data.retrofit.models.Invitation
import com.example.workhub.data.retrofit.models.Job
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.NetworkViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchJobCard(
    job: Job,
    navController: NavHostController,
    workHubViewModel: WorkHubViewModel
) {
    Card(
        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
            0xFFEEEEEE
        ),
        shape = Shapes.large,
        onClick = {
            workHubViewModel.setJobId(job_id = job._id)

            navController.navigate("Job Post") {
                launchSingleTop = true
                restoreState = true
            }
        }, modifier = Modifier.padding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            IconButton(
                onClick = {
                    workHubViewModel.setJobId(job_id = job._id)

                    navController.navigate("Job Post") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                PageImage(image_name = job.page_image, size = 60, horizontal_padding = 5)
            }

            Column {
                Text(text = job.title, fontSize = 20.sp)

                Text(text = job.page, fontSize = 14.sp)

                Text(text = job.location, fontSize = 14.sp)

                Text(text = job.workplace_type, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}