package com.example.workhub.ui.elements.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Person
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
import com.example.workhub.ui.elements.composables.ChatCard
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.ChatsViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatsScreen(
    workHubViewModel: WorkHubViewModel,
    chatsViewModel: ChatsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val chatsUiState by chatsViewModel.uiState.collectAsState()
    val chats by chatsViewModel.getChats(user = uiState.curr_user?.email ?: "")
        .collectAsState(initial = emptyList())
    chatsViewModel.getUsers(uiState.curr_user?.email ?: "", chats)

    LaunchedEffect(Unit) {
        chatsViewModel.setUser(uiState.curr_user?.email ?: "")
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("Select New Chat") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.AddComment, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp),
        ) {
            if(chats.isNotEmpty() && chatsUiState.users.isNotEmpty()) {
                for (i in chats.indices) {
                    item {
                        ChatCard(
                            chat = chats[i],
                            user = chatsUiState.users[i],
                            workHubViewModel = workHubViewModel,
                            navController = navController
                        )

                        Divider()
                    }
                }
            }
        }
    }
}