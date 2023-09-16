package com.example.workhub.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.workhub.*
import com.example.workhub.ui.elements.screens.*
import com.example.workhub.ui.elements.theme.WorkHubTheme
import com.example.workhub.ui.stateholders.LoginEvent
import com.example.workhub.ui.stateholders.OnEvent
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun WorkHubApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val currentRoute = currentDestination?.route ?: "Loading"
    val workHubViewModel: WorkHubViewModel = viewModel()
    val uiState by workHubViewModel.uiState.collectAsState()
    val context = LocalContext.current

    OnEvent(workHubViewModel.event) {
        when (it) {
            LoginEvent.LoginSuccess -> {
                navController.navigate(HomeDestination.route) {
                    launchSingleTop = true
                    restoreState = false
                    popUpTo(HomeDestination.route) {
                        saveState = false
                        inclusive = false
                    }
                }
            }
            is LoginEvent.LoginFailure ->
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        workHubViewModel.getLoggedUser()
    }

    WorkHubTheme {
        Scaffold(
            topBar = {
                if (uiState.curr_user != null) {
                    TopAppBar(
                        modifier = Modifier.height(70.dp),
                        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020)
                        else Color(0xFFEEEEEE)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                navController.navigate("Profile") {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(HomeDestination.route) {
                                        saveState = false
                                        inclusive = false
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(54.dp)
                                        .weight(1f),
                                    tint = if (isSystemInDarkTheme()) Color.White else Color.Black,
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .weight(3f)
                            ) {
                                OutlinedTextField(
                                    value = "",
                                    placeholder = {
                                        Row {
                                            Icon(
                                                imageVector = Icons.Default.Search,
                                                contentDescription = "search"
                                            )
                                            Text(text = "Search")
                                        }
                                    },
                                    onValueChange = {}
                                )
                            }

                            Button(
                                onClick = {
                                    navController.navigate("Search") {
                                        launchSingleTop = true
                                        restoreState = false
                                        popUpTo(HomeDestination.route) {
                                            saveState = false
                                            inclusive = false
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "Search", color = Color.White)
                            }
                        }
                    }
                }
            },
            bottomBar = {
                if (uiState.curr_user != null) {
                    BottomNavigation(
                        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020)
                        else Color(0xFFEEEEEE)
                    ) {
                        snippetDestinations.forEach { navDestination ->
                            BottomNavigationItem(
                                icon = {
                                    if (navDestination.route == "Network" || navDestination.route == "Chats") {
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
                                    } else {
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
                                    Log.d("print", newRoute)

                                    navController.navigate(newRoute) {
                                        launchSingleTop = true
                                        restoreState = true
                                        popUpTo(HomeDestination.route) {
                                            saveState = false
                                            inclusive = false
                                        }
                                    }
                                },
                            )
                        }
                    }
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = "Loading",
                modifier = Modifier.padding(it),
            ) {
                composable(route = HomeDestination.route) {
                    HomeScreen(
                        navController = navController
                    )
                }

                composable(route = NetworkDestination.route) {
                    NetworkScreen(
                        navController = navController
                    )
                }

                composable(route = PostDestination.route) {
                    NewPostScreen()
                }

                composable(route = JobsDestination.route) {
                    JobsScreen(
                        navController = navController
                    )
                }

                composable(route = ChatsDestination.route) {
                    ChatsScreen(
                        navController = navController
                    )
                }

                composable(route = "Profile") {
                    ProfileScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = "Saved Jobs") {
                    SavedJobsScreen(
                        navController = navController
                    )
                }

                composable(route = "Single Chat") {
                    ChatScreen(
                        navController = navController
                    )
                }

                composable(route = "Comments") {
                    CommentsScreen()
                }

                composable(route = "Manage Network") {
                    ManageNetworkScreen(
                        navController = navController
                    )
                }

                composable(route = "Invitations") {
                    InvitationsScreen(
                        navController = navController
                    )
                }

                composable(route = "New Job Post") {
                    PostJobScreen()
                }

                composable(route = "Job Post") {
                    JobScreen(
                        navController = navController
                    )
                }

                composable(route = "Edit Profile") {
                    EditProfileScreen()
                }

                composable(route = "Page") {
                    PageScreen(
                        navController = navController
                    )
                }

                composable(route = "Search") {
                    SearchScreen(
                        navController = navController
                    )
                }

                composable(route = "Select New Chat") {
                    SelectNewChatScreen(
                        navController = navController
                    )
                }

                composable(route = "Login") {
                    LoginScreen(
                        workHubViewModel = workHubViewModel
                    )
                }

                composable(route = "Registration") {
                    RegistrationScreen(
                        workHubViewModel = workHubViewModel
                    )
                }

                composable(route = "Loading") {
                    LoadingScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}