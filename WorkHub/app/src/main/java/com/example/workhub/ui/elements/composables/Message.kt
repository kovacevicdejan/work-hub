package com.example.workhub.ui.elements.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workhub.data.localdb.LocalMessage
import com.example.workhub.data.retrofit.models.User
import java.util.*

fun getHour(timestamp: Long): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    return calendar.get(Calendar.HOUR_OF_DAY)
}

fun getMinute(timestamp: Long): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    return calendar.get(Calendar.MINUTE)
}

@Composable
fun Message(
    message: LocalMessage, user: User
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 5.dp)
        ) {
            ProfileImage(
                image_name = user.profile_image,
                size = 30,
                horizontal_padding = 5
            )

            Text(
                text = "${user.firstname} ${user.lastname} | " +
                        "${getHour(message.timestamp)}:${getMinute(message.timestamp)}",
                fontSize = 20.sp
            )
        }

        Column(modifier = Modifier.padding(40.dp, 0.dp, 0.dp, 0.dp)) {
            Text(
                text = message.text, fontSize = 18.sp, maxLines = Int.MAX_VALUE
            )
        }
    }
}