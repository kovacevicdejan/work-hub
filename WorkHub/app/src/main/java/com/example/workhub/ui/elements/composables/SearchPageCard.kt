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
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.NetworkViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun SearchPageCard(
    page: Page,
    navController: NavHostController,
    workHubViewModel: WorkHubViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
            0xFFEEEEEE
        ),
        shape = Shapes.large
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            IconButton(
                onClick = {
                    workHubViewModel.setPage(page = page.name)

                    navController.navigate("Page") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                PageImage(image_name = page.profile_image, size = 60, horizontal_padding = 5)
            }

            Column {
                Text(text = page.name, fontSize = 20.sp)

                Text(
                    text = page.headline,
                    fontSize = 14.sp
                )

                Text(
                    text = "${page.followers.size} followers",
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}