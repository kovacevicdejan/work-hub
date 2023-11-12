package com.example.workhub.ui.elements.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.ManageNetworkViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FollowedPageCard(
    page: Page,
    curr_user: User?,
    navController: NavHostController,
    manageNetworkViewModel: ManageNetworkViewModel,
    workHubViewModel: WorkHubViewModel
) {
    Card(
        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
            0xFFEEEEEE
        ),
        shape = Shapes.large,
        onClick = {
            workHubViewModel.setPage(page.name)

            navController.navigate("Page") {
                launchSingleTop = true
                restoreState = true
            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            PageImage(image_name = page.profile_image, size = 60, horizontal_padding = 5)

            Column {
                Text(text = page.name, fontSize = 20.sp)

                Text(
                    text = "${page.followers.size} followers",
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (curr_user != null) {
                        manageNetworkViewModel.unfollow(
                            user = curr_user.email,
                            name = page.name
                        )
                    }
                },
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            {
                Text(text = "Unfollow", color = Color.White)
            }
        }
    }
}