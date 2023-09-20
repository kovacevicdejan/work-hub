package com.example.workhub.ui.elements.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
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
import com.example.workhub.ui.stateholders.WorkHubViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConnectionCard(
    connection: User,
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
            workHubViewModel.setUser(connection.email)

            navController.navigate("Profile") {
                launchSingleTop = true
                restoreState = true
                popUpTo(HomeDestination.route) {
                    saveState = false
                    inclusive = false
                }
            }
        }
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

            Spacer(modifier = Modifier.weight(1f))

            if (curr_user != null) {
                if(connection != curr_user) {
                    Button(
                        onClick = {
                            manageNetworkViewModel.removeConnection(
                                user1 = curr_user.email,
                                user2 = connection.email
                            )
                        },
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Text(text = "Remove", color = Color.White)
                    }
                }
            }
        }
    }
}