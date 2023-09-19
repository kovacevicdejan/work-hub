package com.example.workhub.ui.elements.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.example.workhub.ui.elements.composables.PageImage
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.PageViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PageScreen (
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
        pageViewModel.getPage(uiState.page)
    }

    Scaffold(
        bottomBar = {
            if(state == 3) {
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
                                value = "",
                                onValueChange = {},
                                modifier = Modifier
                                    .weight(5.6f)
                                    .padding(horizontal = 10.dp)
                            )

                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(0.dp, 0.dp, 10.dp, 0.dp)
                            ) {
//                            Text(text = "Send", color = Color.White)
                                Icon(imageVector = Icons.Default.Send, contentDescription = null)
                            }
                        }

                        Divider()
                    }
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
                            PageImage(image_name = pageUiState.page?.profile_image ?: "", size = 100)
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
                                        val webIntent = Intent(Intent.ACTION_VIEW,
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
                                Button(
                                    onClick = {
                                        navController.navigate("Edit Profile") {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                ) {
                                    Text(text = "Follow", color = Color.White)
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
                    )
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
                                        text = "IT fields: ",
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        fontSize = 16.sp
                                    )

                                    Text(
                                        text = pageUiState.page?.industry ?: "",
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
//                    items(count = 3) {
//                        Post(navController = navController)
//                    }
                }

                2 -> {
                    for (i in 0..7) {
                        item {
                            Card(
                                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                                    0xFFEEEEEE
                                ),
                                shape = Shapes.large,
                                onClick = {
                                    navController.navigate("Job Post") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 5.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(60.dp)
                                            .height(60.dp)
                                    )

                                    Column {
                                        Text(text = "Senior Software engineer", fontSize = 20.sp)

                                        Text(text = "TomTom", fontSize = 14.sp)

                                        Text(text = "Belgrade, Serbia", fontSize = 14.sp)

                                        Text(text = "Hybrid", fontSize = 14.sp)
                                    }

                                    Spacer(modifier = Modifier.weight(1f))

                                    Button(
                                        onClick = {},
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    ) {
                                        Text(text = "Save", color = Color.White)
                                    }
                                }
                            }

                            Divider()
                        }
                    }
                }

                3 -> {
                    item {
                        for (i in 1..5) {
                            Card(
                                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                                    0xFFEEEEEE
                                ),
                                modifier = Modifier
                                    .padding(
                                        0.dp,
                                        0.dp,
                                        0.dp,
                                        if (i == 5) 85.dp else 10.dp
                                    )
                                    .fillMaxWidth(),
                                shape = Shapes.large
                            ) {
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .width(30.dp)
                                                .height(30.dp)
                                        )

                                        Text(text = "Petar Petrovic", fontSize = 20.sp)
                                    }

                                    Column(modifier = Modifier.padding(30.dp, 0.dp, 0.dp, 5.dp)) {
                                        Text(
                                            text = "I saw you got a new job at my company. Congratulations!",
                                            fontSize = 18.sp,
                                            maxLines = Int.MAX_VALUE
                                        )

                                        Text(
                                            text = "Can we meet for a drink?",
                                            fontSize = 18.sp,
                                            maxLines = Int.MAX_VALUE
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
}