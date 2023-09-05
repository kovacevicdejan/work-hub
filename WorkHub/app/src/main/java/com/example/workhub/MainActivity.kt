package com.example.workhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.workhub.ui.elements.screens.*
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
            topBar = {
                TopAppBar(
                    modifier = Modifier.height(70.dp),
                    backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020)
                    else Color(0xFFEEEEEE)) {
                    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            navController.navigate("Profile") {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(HomeDestination.route) {
                                    saveState = true
                                    inclusive = false
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(54.dp).weight(1f),
                                tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 10.dp).weight(3f)){
                            OutlinedTextField(
                                value = "",
                                placeholder = {
                                    Row {
                                        Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                                        Text(text = "Search")
                                    }
                                },
                                onValueChange = {}
                            )
                        }

                        Button(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                            Text(text = "Search", color = Color.White)
                        }
                    }
                }
            },
            bottomBar = {
                BottomNavigation(
                    backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020)
                    else Color(0xFFEEEEEE)) {
                    snippetDestinations.forEach { navDestination ->
                        BottomNavigationItem(
                            icon = {
                                if(navDestination.route == "Network" || navDestination.route == "Chats") {
                                    BadgedBox(
                                        badge = {
                                            Badge(backgroundColor = Color.Red) { Text("8") }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = navDestination.icon,
                                            contentDescription = null,
                                        )
                                    }
                                }
                                else {
                                    Icon(
                                        imageVector = navDestination.icon,
                                        contentDescription = null,
                                    )
                                }
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
                composable(route = PostDestination.route) {
                    NewPostScreen(viewModelFromActivity = viewModelFromActivity)
                }
                composable(route = JobsDestination.route) {
                    JobsScreen(
                        viewModelFromActivity = viewModelFromActivity,
                        navController = navController
                    )
                }
                composable(route = ChatsDestination.route) {
                    ChatsScreen(
                        viewModelFromActivity = viewModelFromActivity,
                        navController = navController
                    )
                }
                composable(route = "Profile") {
                    ProfileScreen(viewModelFromActivity = viewModelFromActivity)
                }
                composable(route = "Saved Jobs") {
                    SavedJobsScreen(viewModelFromActivity = viewModelFromActivity)
                }
                composable(route = "Chat") {
                    ChatScreen(viewModelFromActivity = viewModelFromActivity)
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