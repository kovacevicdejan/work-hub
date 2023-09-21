package com.example.workhub.ui

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.example.workhub.ui.elements.composables.ProfileImage
import com.example.workhub.ui.elements.screens.*
import com.example.workhub.ui.elements.theme.WorkHubTheme
import com.example.workhub.ui.stateholders.ChatEvent
import com.example.workhub.ui.stateholders.OnEvent
import com.example.workhub.ui.stateholders.SignInEvent
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
            SignInEvent.SignInSuccess -> {
                navController.navigate(HomeDestination.route) {
                    launchSingleTop = true
                    restoreState = false
                    popUpTo(HomeDestination.route) {
                        saveState = false
                        inclusive = false
                    }
                }
            }

            ChatEvent.NewMessageEvent -> {
                Toast.makeText(
                    context,
                    "New message!",
                    Toast.LENGTH_SHORT
                ).show()
            }
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
                                workHubViewModel.setUser(uiState.curr_user!!.email)

                                navController.navigate("Profile") {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(HomeDestination.route) {
                                        saveState = false
                                        inclusive = false
                                    }
                                }
                            }) {
                                ProfileImage(
                                    image_name = uiState.curr_user?.profile_image ?: "",
                                    size = 55,
                                    horizontal_padding = 0
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .weight(3f)
                            ) {
                                OutlinedTextField(
                                    value = uiState.keyword,
                                    placeholder = {
                                        Row {
                                            Icon(
                                                imageVector = Icons.Default.Search,
                                                contentDescription = "search"
                                            )
                                            Text(text = "Search")
                                        }
                                    },
                                    onValueChange = {workHubViewModel.setKeyword(it)}
                                )
                            }

                            Button(
                                onClick = {
                                    workHubViewModel.setKeyword(uiState.keyword)

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
                                    Icon(
                                        imageVector = navDestination.icon,
                                        contentDescription = null,
                                    )
                                },
                                label = { Text(navDestination.route, fontSize = 11.sp) },
                                selected = currentRoute.startsWith(navDestination.route),
                                onClick = {
                                    val newRoute = navDestination.route

                                    if(newRoute == PostDestination.route) {
                                        workHubViewModel.setPostCreator(uiState.curr_user?.email ?: "")
                                        workHubViewModel.setCreatorType(0)
                                    }

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
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = NetworkDestination.route) {
                    NetworkScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = PostDestination.route) {
                    NewPostScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = JobsDestination.route) {
                    JobsScreen(
                        navController = navController
                    )
                }

                composable(route = ChatsDestination.route) {
                    ChatsScreen(
                        navController = navController,
                        workHubViewModel = workHubViewModel
                    )
                }

                composable(route = "Sign In") {
                    SignInScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
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

                composable(route = "Profile") {
                    ProfileScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = "Single Chat") {
                    ChatScreen(
                        workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = "Comments") {
                    CommentsScreen()
                }

                composable(route = "Manage Network") {
                    ManageNetworkScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = "Invitations") {
                    InvitationsScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = "New Job Post") {
                    PostJobScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = "Job Post") {
                    JobScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = "Edit Profile") {
                    EditProfileScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = "Page") {
                    PageScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = "Search") {
                    SearchScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = "Select New Chat") {
                    SelectNewChatScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = "User Posts") {
                    UserPostsScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = "Add Experience") {
                    AddExperienceScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }

                composable(route = "Create Page") {
                    CreatePageScreen(
                        workHubViewModel = workHubViewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}