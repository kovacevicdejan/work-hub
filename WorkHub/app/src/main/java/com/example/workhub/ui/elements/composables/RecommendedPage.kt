package com.example.workhub.ui.elements.composables

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
import com.example.workhub.data.retrofit.models.*
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.stateholders.NetworkViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendedPage(
    user: User,
    page: Page,
    navController: NavHostController,
    networkViewModel: NetworkViewModel,
    workHubViewModel: WorkHubViewModel

) {
    Card(
        onClick = {
            workHubViewModel.setPage(page.name)

            navController.navigate("Page") {
                launchSingleTop = true
                restoreState = true
            }
        }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PageImage(
                image_name = page.profile_image,
                size = 70,
                vertical_padding = 5
            )

            Text(text = page.name, fontSize = 20.sp)

            Text(
                text = "${page.followers.size} followers",
                fontSize = 12.sp
            )

            if (!page.followers.contains(Follower(user = user.email))) {
                Button(
                    onClick = {
                        networkViewModel.follow(
                            user = user.email,
                            name = page.name
                        )
                    }
                ) {
                    Text(text = "Follow", color = Color.White)
                }
            }
            else {
                Text(
                    text = "Following",
                    color = Blue,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}