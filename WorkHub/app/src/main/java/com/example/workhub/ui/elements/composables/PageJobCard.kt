package com.example.workhub.ui.elements.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workhub.data.retrofit.models.Applicant
import com.example.workhub.data.retrofit.models.Job
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.PageViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PageJobCard(
    job: Job,
    page: Page,
    navController: NavHostController,
    workHubViewModel: WorkHubViewModel,
    pageViewModel: PageViewModel
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val pageUiState by pageViewModel.uiState.collectAsState()

    Card(
        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
            0xFFEEEEEE
        ),
        shape = Shapes.large,
        onClick = {
            workHubViewModel.setJobId(job._id)

            navController.navigate("Job Post") {
                launchSingleTop = true
                restoreState = true
            }
        },
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 5.dp)
        ) {
            PageImage(image_name = page.profile_image, size = 60)

            Column {
                Text(text = job.title, fontSize = 20.sp)

                Text(text = job.page, fontSize = 14.sp)

                Text(text = job.location, fontSize = 14.sp)

                Text(text = job.workplace_type, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            if(pageUiState.page?.admin == (uiState.curr_user?.email ?: "")) {
                Button(
                    onClick = {
                        pageViewModel.deleteJob(job_id = job._id)
                    },
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(text = "Delete", color = Color.White)
                }
            } else {
                Button(
                    onClick = {
                        pageViewModel.applyForJob(
                            user = uiState.curr_user?.email ?: "",
                            job_id = job._id)
                    },
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(text = "Apply", color = Color.White)
                }
            }
        }
    }
}