package com.example.workhub.ui.elements.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.workhub.HomeDestination
import com.example.workhub.ui.stateholders.GetUserEvent
import com.example.workhub.ui.stateholders.LoginEvent
import com.example.workhub.ui.stateholders.OnEvent
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun LoadingScreen(
    workHubViewModel: WorkHubViewModel,
    navController: NavHostController
) {
    OnEvent(workHubViewModel.event) {
        when (it) {
            GetUserEvent.GetUserSuccess -> {
                navController.navigate(HomeDestination.route) {
                    launchSingleTop = true
                    restoreState = false
                    popUpTo(HomeDestination.route) {
                        saveState = false
                        inclusive = false
                    }
                }
            }

            GetUserEvent.GetUserFailure -> {
                Log.d("print", "kjdcbjsd")
                navController.navigate("Login") {
                    launchSingleTop = true
                    restoreState = false
                    popUpTo(HomeDestination.route) {
                        saveState = false
                        inclusive = false
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Black else Color.White),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}