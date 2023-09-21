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
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.ManageNetworkViewModel
import com.example.workhub.ui.stateholders.SelectNewChatViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatConnectionCard(
    connection: User,
    navController: NavHostController,
    selectNewChatViewModel: SelectNewChatViewModel,
    workHubViewModel: WorkHubViewModel
) {
    val uiState by workHubViewModel.uiState.collectAsState()

    Card(
        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
        shape = Shapes.large,
        onClick = {
            selectNewChatViewModel.newChat(
                user1 = uiState.curr_user?.email ?: "",
                user2 = connection.email
            )
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            ProfileImage(
                image_name = connection.profile_image,
                size = 60,
                horizontal_padding = 5
            )

            Column {
                Text(text = connection.firstname + " " + connection.lastname, fontSize = 20.sp)

                Text(
                    text = connection.headline,
                    fontSize = 14.sp
                )
            }
        }
    }
}