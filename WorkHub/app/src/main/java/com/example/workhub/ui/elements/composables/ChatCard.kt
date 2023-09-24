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
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.ChatsViewModel
import com.example.workhub.ui.stateholders.ManageNetworkViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel
import kotlinx.coroutines.Job
import java.util.*

fun getYear(timestamp: Long): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    return calendar.get(Calendar.YEAR)
}

fun getMonth(timestamp: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    return when(calendar.get(Calendar.MONTH) + 1) {
        1 -> "JAN"
        2 -> "FEB"
        3 -> "MAR"
        4 -> "APR"
        5 -> "MAY"
        6 -> "JUN"
        7 -> "JUL"
        8 -> "AUG"
        9 -> "SEP"
        10 -> "OCT"
        11 -> "NOV"
        12 -> "DEC"
        else -> "INVALID"
    }
}

fun getDay(timestamp: Long): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    return calendar.get(Calendar.DAY_OF_MONTH)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatCard(
    chat: LocalChat,
    user: User,
    navController: NavHostController,
    workHubViewModel: WorkHubViewModel,
    unread_messages_count: Int,
    chatsViewModel: ChatsViewModel
) {
    Card(
        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
        shape = Shapes.large,
        onClick = {
            workHubViewModel.setChatId(chat.id)
            chatsViewModel.readMessages(chat)

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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = user.firstname + " " + user.lastname, fontSize = 20.sp)

                    if(unread_messages_count > 0)
                        Badge(backgroundColor = Blue, contentColor = Color.White, modifier = Modifier.padding(horizontal = 5.dp)) {
                            Text("$unread_messages_count", fontSize = 10.sp)
                        }
                }

                Text(
                    text = user.headline,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${getDay(chat.timestamp)}.${getMonth(chat.timestamp)}.${getYear(chat.timestamp)}.",
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}