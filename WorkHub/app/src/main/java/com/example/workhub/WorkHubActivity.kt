package com.example.workhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.workhub.ui.SocketManager
import com.example.workhub.ui.WorkHubApp
import com.example.workhub.ui.elements.theme.WorkHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkHubActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkHubTheme {
                WorkHubApp()
            }
        }

        SocketManager.connect()
    }

    override fun onDestroy() {
        SocketManager.disconnect()
        super.onDestroy()
    }
}