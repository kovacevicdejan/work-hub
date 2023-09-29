package com.example.workhub.ui.elements.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.workhub.PostDestination
import com.example.workhub.data.retrofit.models.FollowedPage
import com.example.workhub.ui.elements.composables.PageImage
import com.example.workhub.ui.elements.composables.PageJobCard
import com.example.workhub.ui.elements.composables.Post
import com.example.workhub.ui.elements.composables.ReviewCard
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.OnEvent
import com.example.workhub.ui.stateholders.PageEvent
import com.example.workhub.ui.stateholders.PageViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PageScreen(
    workHubViewModel: WorkHubViewModel,
    pageViewModel: PageViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val pageUiState by pageViewModel.uiState.collectAsState()
    var state by remember { mutableStateOf(0) }
    val titles = listOf("ABOUT", "POSTS", "JOBS", "REVIEWS")
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        pageViewModel.getPage(
            name = uiState.page,
            user = uiState.curr_user?.email ?: ""
        )
    }

    OnEvent(pageViewModel.event) {
        when(it) {
            PageEvent.FollowPageEvent -> {
                workHubViewModel.getLoggedUser()
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (state == 3 && (pageUiState.page?.admin ?: "") != (uiState.curr_user?.email ?: "")) {
                Card(
                    backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                        0xFFEEEEEE
                    ),
                    shape = Shapes.large
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            OutlinedTextField(
                                value = pageUiState.text,
                                onValueChange = {pageViewModel.setText(it)},
                                modifier = Modifier
                                    .weight(5.6f)
                                    .padding(horizontal = 10.dp)
                            )

                            Button(
                                onClick = {
                                    pageViewModel.sendPageReview(
                                        user = (uiState.curr_user?.firstname ?: "") + " " + (uiState.curr_user?.lastname ?: ""),
                                        user_image = uiState.curr_user?.profile_image ?: "",
                                        text = pageUiState.text
                                    )

                                    pageViewModel.setText("")
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
        },
        floatingActionButton = {
            if(state == 1) {
                FloatingActionButton(
                    onClick = {
                        workHubViewModel.setPostCreator(pageUiState.page?.name ?: "")
                        workHubViewModel.setCreatorType(1)

                        navController.navigate(PostDestination.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
            else if(state == 2 && (pageUiState.page?.admin ?: "") == (uiState.curr_user?.email
                    ?: "")
            ) {
                FloatingActionButton(
                    onClick = {
                        workHubViewModel.setPageImage(pageUiState.page?.profile_image ?: "")

                        navController.navigate("New Job Post") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        }
    ) {
        LazyColumn {
            item {
                Card(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                        0xFFEEEEEE
                    ),
                    shape = Shapes.large
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            PageImage(
                                image_name = pageUiState.page?.profile_image ?: "",
                                size = 100
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = pageUiState.page?.name ?: "",
                                    fontSize = 30.sp,
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )

                                Text(
                                    text = pageUiState.page?.headline ?: "",
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )

                                Text(
                                    text = pageUiState.page?.location ?: "",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )

                                Text(
                                    text = "${pageUiState.page?.followers?.size ?: 0} followers",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Button(
                                    onClick = {
                                        val webIntent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://${pageUiState.page?.website ?: ""}")
                                        )
                                        startActivity(context, webIntent, null)
                                    },
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                ) {
                                    Text(text = "View website", color = Color.White)
                                }
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (!uiState.curr_user?.followed_pages?.contains(
                                        FollowedPage(
                                            name = pageUiState.page?.name ?: ""
                                        )
                                    )!!
                                ) {
                                    Button(
                                        onClick = {
                                            pageViewModel.follow(
                                                user = uiState.curr_user?.email ?: ""
                                            )
                                        },
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp)
                                    ) {
                                        Text(text = "Follow", color = Color.White)
                                    }
                                }
                                else {
                                    Text(
                                        text = "Following",
                                        color = Blue,
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                TabRow(
                    selectedTabIndex = state,
                    backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                        0xFFEEEEEE
                    ),
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, if(state == 1) 5.dp else 0.dp)
                ) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = state == index,
                            onClick = { state = index }
                        )
                    }
                }
            }

            when (state) {
                0 -> {
                    item {
                        Card(
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 0.dp, 10.dp)
                                .fillMaxWidth(),
                            backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                                0xFFEEEEEE
                            ),
                            shape = Shapes.large
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)
                                ) {
                                    Text(text = "Overview", fontSize = 20.sp)
                                }

                                Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                                    Text(
                                        text = pageUiState.page?.about ?: "",
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        fontSize = 16.sp
                                    )
                                }

                                Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                                    Text(
                                        text = "Website: ",
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        fontSize = 16.sp
                                    )

                                    Text(
                                        text = pageUiState.page?.website ?: "",
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        fontSize = 16.sp
                                    )
                                }

                                Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                                    Text(
                                        text = "Specialties: ",
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        fontSize = 16.sp
                                    )

                                    Text(
                                        text = pageUiState.page?.specialties ?: "",
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        fontSize = 16.sp
                                    )
                                }

                                Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                                    Text(
                                        text = "Headquarters: ",
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        fontSize = 16.sp
                                    )

                                    Text(
                                        text = pageUiState.page?.location ?: "",
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }

                1 -> {
                    item {
                        if (pageUiState.page != null && uiState.curr_user != null) {
                            for (post in pageUiState.posts) {
                                Post(
                                    post = post,
                                    workHubViewModel = workHubViewModel,
                                    navController = navController,
                                    page = pageUiState.page,
                                    user = null
                                )
                            }
                        }
                    }
                }

                2 -> {
                    for (job in pageUiState.jobs) {
                        item {
                            pageUiState.page?.let { it1 ->
                                PageJobCard(
                                    job = job,
                                    page = it1,
                                    navController = navController,
                                    pageViewModel = pageViewModel,
                                    workHubViewModel = workHubViewModel
                                )
                            }

                            Divider()
                        }
                    }
                }

                3 -> {
                    if(pageUiState.page != null) {
                        for(i in pageUiState.page!!.reviews.size - 1 downTo 0) {
                            item {
                                ReviewCard(
                                    review = pageUiState.page!!.reviews[i],
                                    first = i == 0
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}