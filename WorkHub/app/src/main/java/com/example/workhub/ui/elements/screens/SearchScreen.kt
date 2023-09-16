package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workhub.ui.elements.theme.Shapes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    navController: NavHostController
) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("PEOPLE", "PAGES", "JOBS")

    LazyColumn {
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

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
            ) {
                Text(text = "2343 search results")
            }
        }

        when (state) {
            0 -> {
                item {
                    for(i in 1..10) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                                0xFFEEEEEE
                            ),
                            shape = Shapes.large
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 10.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        navController.navigate("Profile") {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier.size(60.dp)
                                    )
                                }

                                Column {
                                    Text(text = "Petar Petrovic", fontSize = 20.sp)

                                    Text(text = "Software engineer at Nutanix", fontSize = 14.sp)
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Button(onClick = {}, modifier = Modifier.padding(horizontal = 10.dp))
                                {
                                    Text(text = "Connect", color = Color.White)
                                }
                            }
                        }

                        Divider()
                    }
                }
            }

            1 -> {
                item {
                    for(i in 1..10) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                                0xFFEEEEEE
                            ),
                            shape = Shapes.large
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 10.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        navController.navigate("Profile") {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier.size(60.dp)
                                    )
                                }

                                Column {
                                    Text(text = "TomTom", fontSize = 20.sp)

                                    Text(
                                        text = "IT services and IT consulting.",
                                        fontSize = 14.sp
                                    )

                                    Text(
                                        text = "200000 followers",
                                        fontSize = 14.sp
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Button(onClick = {}, modifier = Modifier.padding(horizontal = 10.dp))
                                {
                                    Text(text = "Follow", color = Color.White)
                                }
                            }
                        }

                        Divider()
                    }
                }
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