package com.example.workhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.workhub.ui.elements.screens.HomeScreen
import com.example.workhub.ui.elements.screens.NetworkScreen
import com.example.workhub.ui.elements.screens.ProfileScreen
import com.example.workhub.ui.elements.theme.WorkHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnippetApp()
        }
    }
}

@Composable
fun SnippetApp() {
    val viewModelFromActivity: SnippetViewModel = viewModel()

    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val currentRoute = currentDestination?.route ?: HomeDestination.route

    WorkHubTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(backgroundColor = Color(0xFF202020)) {
                    snippetDestinations.forEach { navDestination ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    imageVector = navDestination.icon,
                                    contentDescription = null,
                                )
                            },
                            label = { Text(navDestination.route, fontSize = 11.sp) },
                            selected = currentRoute.startsWith(navDestination.route),
                            onClick = {
                                val newRoute = navDestination.route

                                navController.navigate(newRoute) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(HomeDestination.route) {
                                        saveState = true
                                        inclusive = false
                                    }
                                }
                            },
                        )
                    }
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = HomeDestination.route,
                modifier = Modifier.padding(it),
            ) {
                composable(route = HomeDestination.route) {
                    HomeScreen(viewModelFromActivity = viewModelFromActivity)
                }
                composable(route = NetworkDestination.route) {
                    NetworkScreen(viewModelFromActivity = viewModelFromActivity)
                }
                composable(
                    route = ProfileDestination.route,
                ) {
                    ProfileScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WorkHubTheme {
        SnippetApp()
    }
}