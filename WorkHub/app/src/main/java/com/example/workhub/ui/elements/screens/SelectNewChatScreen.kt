package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.example.workhub.ChatsDestination
import com.example.workhub.data.retrofit.WorkHubApi
import com.example.workhub.ui.elements.composables.ChatConnectionCard
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.ChatEvent
import com.example.workhub.ui.stateholders.OnEvent
import com.example.workhub.ui.stateholders.SelectNewChatViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun SelectNewChatScreen(
    workHubViewModel: WorkHubViewModel,
    selectNewChatViewModel: SelectNewChatViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val selectNewChatUiState by selectNewChatViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        uiState.curr_user?.let { selectNewChatViewModel.getUser(it.email) }
    }

    OnEvent(selectNewChatViewModel.event) {
        if(it == ChatEvent.NewChatEvent) {
            workHubViewModel.setChatId(chat_id = selectNewChatUiState.chat_id)

            navController.navigate("Single Chat") {
                launchSingleTop = true
                restoreState = false
            }
        }
    }

    LazyColumn {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
            ) {
                Text(text = "Select connection for new chat", fontSize = 20.sp)
            }
        }

        for (connection in selectNewChatUiState.connections) {
            item {
                ChatConnectionCard(
                    connection = connection,
                    navController = navController,
                    selectNewChatViewModel = selectNewChatViewModel,
                    workHubViewModel = workHubViewModel
                )

                Divider()
            }
        }
    }
}