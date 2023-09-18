package com.example.workhub.ui.elements.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workhub.data.retrofit.models.Invitation
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.stateholders.NetworkViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendedUser(
    user1: User,
    user2: User,
    navController: NavHostController,
    networkViewModel: NetworkViewModel
) {
    Card(
        onClick = {
            navController.navigate("Profile") {
                launchSingleTop = true
                restoreState = true
            }
        },
        modifier = Modifier.height(200.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ProfileImage(
                image_name = user2.profile_image,
                size = 70,
                vertical_padding = 5
            )

            Text(
                text = user2.firstname + " " + user2.lastname,
                fontSize = 20.sp
            )

            Text(
                text = user2.headline,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = "20 mutual connections",
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            if (!user1.received_invitations.contains(
                    Invitation(user = user2.email)
                )
            )
                Button(
                    onClick = {
                        networkViewModel.connect(
                            user1 = user1.email,
                            user2 = user2.email
                        )
                    }
                ) {
                    Text(text = "Connect", color = Color.White)
                }
            else
                Text(
                    text = "Pending",
                    color = Blue,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
        }
    }
}