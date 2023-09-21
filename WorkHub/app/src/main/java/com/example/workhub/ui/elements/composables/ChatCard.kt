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
import com.example.workhub.HomeDestination
import com.example.workhub.data.localdb.LocalChat
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.ManageNetworkViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatCard(
    chat: LocalChat,
    user: User,
    navController: NavHostController,
    workHubViewModel: WorkHubViewModel
) {
    Card(
        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
        shape = Shapes.large,
        onClick = {
            workHubViewModel.setChatId(chat.id)

            navController.navigate("Single Chat") {
                launchSingleTop = true
                restoreState = true
            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            ProfileImage(
                image_name = user.profile_image,
                size = 60,
                horizontal_padding = 5
            )

            Column {
                Text(text = user.firstname + " " + user.lastname, fontSize = 20.sp)

                Text(
                    text = "Thank you! It means a lot to me.",
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "27.8.2023.",
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}