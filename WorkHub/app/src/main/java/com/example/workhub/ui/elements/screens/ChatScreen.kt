package com.example.workhub.ui.elements.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.workhub.ui.elements.composables.Message
import com.example.workhub.ui.elements.composables.ProfileImage
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.ChatViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    workHubViewModel: WorkHubViewModel,
    chatViewModel: ChatViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val chatUiState by chatViewModel.uiState.collectAsState()
    val messages by chatViewModel.getMessages(chat_id = uiState.chat_id)
        .collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        chatViewModel.getUsers(
            email = uiState.curr_user?.email ?: "",
            chat_id = uiState.chat_id
        )
    }

    Scaffold(
        bottomBar = {
            Card(
                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
                shape = Shapes.large
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        OutlinedTextField(
                            value = chatUiState.text,
                            onValueChange = {chatViewModel.setText(it)},
                            modifier = Modifier
                                .weight(5.6f)
                                .padding(horizontal = 10.dp)
                        )

                        Button(
                            onClick = {
                                chatViewModel.sendMessage(
                                    text = chatUiState.text,
                                    chat_id = uiState.chat_id
                                )

                                chatViewModel.setText("")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = null)
                        }
                    }

                    Divider()
                }
            }
        }
    ) {
        LazyColumn {
            item {
                Card(
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
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
                                navController.navigate("Profile") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        ) {
                            ProfileImage(
                                image_name = chatUiState.other_user?.profile_image ?: "",
                                size = 60,
                                horizontal_padding = 5
                            )
                        }

                        Column {
                            Text(
                                text = (chatUiState.other_user?.firstname ?: "") + " "
                                        + (chatUiState.other_user?.lastname ?: ""),
                                fontSize = 20.sp
                            )

                            Text(text = chatUiState.other_user?.headline ?: "", fontSize = 14.sp)
                        }
                    }
                }
            }

            if(messages.isNotEmpty() && chatUiState.user != null && chatUiState.other_user != null) {
                val dividedMessages = chatViewModel.divideMessages(messages = messages)

                for(message_list in dividedMessages){
                    val timestamp = message_list[0].timestamp

                    item {
                        Card(
                            backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                                0xFFEEEEEE
                            ),
                            shape = Shapes.large,
                            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, if (message_list == dividedMessages[dividedMessages.size - 1]) 77.dp else 0.dp)
                        ) {
                            Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)) {
                                Divider()

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "${chatViewModel.getMonth(timestamp)} ${chatViewModel.getDay(timestamp)}, ${chatViewModel.getYear(timestamp)}")
                                }

                                for(message in message_list) {
                                    Message(
                                        message = message,
                                        user = if(message.user == (chatUiState.user?.email ?: "")) chatUiState.user!! else chatUiState.other_user!!
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}