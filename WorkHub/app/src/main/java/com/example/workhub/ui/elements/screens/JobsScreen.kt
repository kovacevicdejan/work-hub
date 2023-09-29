package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.workhub.ui.elements.composables.PageImage
import com.example.workhub.ui.elements.theme.Shapes
import com.example.workhub.ui.stateholders.JobsViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun JobsScreen(
    workHubViewModel: WorkHubViewModel,
    jobsViewModel: JobsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val sortTypes = arrayOf("Date posted", "Deadline")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by rememberSaveable { mutableStateOf(sortTypes[0]) }
    var expanded1 by remember { mutableStateOf(false) }
    var selectedText1 by rememberSaveable { mutableStateOf(sortTypes[0]) }
    var expanded2 by remember { mutableStateOf(false) }
    var selectedText2 by rememberSaveable { mutableStateOf(sortTypes[0]) }
    val scope = rememberCoroutineScope()
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)

    val uiState by workHubViewModel.uiState.collectAsState()
    val jobsUiState by jobsViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        jobsViewModel.getJobs()
    }

    Column {
        BottomDrawer(
            gesturesEnabled = false,
            drawerState = drawerState,
            drawerContent = {
                LazyColumn {
                    item {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Filter 1: ",
                                modifier = Modifier.weight(1f)
                            )

                            Box(
                                modifier = Modifier
                                    .weight(2f)
                            ) {
                                ExposedDropdownMenuBox(
                                    expanded = expanded1,
                                    onExpandedChange = {
                                        expanded1 = !expanded1
                                    }
                                ) {
                                    TextField(
                                        value = selectedText1,
                                        onValueChange = {},
                                        readOnly = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expanded1
                                            )
                                        },
                                    )

                                    ExposedDropdownMenu(
                                        expanded = expanded1,
                                        onDismissRequest = { expanded1 = false }
                                    ) {
                                        sortTypes.forEach { item ->
                                            DropdownMenuItem(
                                                onClick = {
                                                    selectedText1 = item
                                                    expanded1 = false
                                                }
                                            ) {
                                                Text(text = item)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Filter 2: ",
                                modifier = Modifier.weight(1f)
                            )

                            Box(
                                modifier = Modifier
                                    .weight(2f)
                            ) {
                                ExposedDropdownMenuBox(
                                    expanded = expanded2,
                                    onExpandedChange = {
                                        expanded2 = !expanded2
                                    }
                                ) {
                                    TextField(
                                        value = selectedText2,
                                        onValueChange = {},
                                        readOnly = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expanded2
                                            )
                                        },
                                    )

                                    ExposedDropdownMenu(
                                        expanded = expanded2,
                                        onDismissRequest = { expanded2 = false }
                                    ) {
                                        sortTypes.forEach { item ->
                                            DropdownMenuItem(
                                                onClick = {
                                                    selectedText2 = item
                                                    expanded2 = false
                                                }
                                            ) {
                                                Text(text = item)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                                Button(
                                    modifier = Modifier.padding(top = 10.dp),
                                    onClick = { scope.launch { drawerState.close() } },
                                ) {
                                    Text(text = "Apply", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        ) {
            Column {
//                Card(
//                    modifier = Modifier
//                        .padding(vertical = 10.dp)
//                        .fillMaxWidth(),
//                    shape = Shapes.large
//                ) {
//                    Column {
//                        Row(
//                            modifier = Modifier.padding(10.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                text = "Sort jobs by: ",
//                                modifier = Modifier.weight(1.2f)
//                            )
//
//                            Box(modifier = Modifier.weight(2.5f)) {
//                                ExposedDropdownMenuBox(
//                                    expanded = expanded,
//                                    onExpandedChange = {
//                                        expanded = !expanded
//                                    }
//                                ) {
//                                    TextField(
//                                        value = selectedText,
//                                        onValueChange = {},
//                                        readOnly = true,
//                                        trailingIcon = {
//                                            ExposedDropdownMenuDefaults.TrailingIcon(
//                                                expanded = expanded
//                                            )
//                                        },
//                                    )
//
//                                    ExposedDropdownMenu(
//                                        expanded = expanded,
//                                        onDismissRequest = { expanded = false }
//                                    ) {
//                                        sortTypes.forEach { item ->
//                                            DropdownMenuItem(
//                                                onClick = {
//                                                    selectedText = item
//                                                    expanded = false
//                                                }
//                                            ) {
//                                                Text(text = item)
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//
//                            IconButton(
//                                onClick = {
//                                    scope.launch { drawerState.open() }
//                                },
//                                modifier = Modifier.weight(0.8f)
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.Tune,
//                                    contentDescription = null,
//                                    modifier = Modifier
//                                        .width(40.dp)
//                                        .height(40.dp)
//                                )
//                            }
//                        }
//                    }
//                }

                LazyColumn {
                    item {
                        Card(
                            backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                                0xFFEEEEEE
                            ),
                            shape = Shapes.large,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Text(text = "Jobs", fontSize = 24.sp)
                            }
                        }

                        Divider()
                    }

                    for (job in jobsUiState.jobs) {
                        item {
                            Card(
                                backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(
                                    0xFFEEEEEE
                                ),
                                shape = Shapes.large,
                                onClick = {
                                    workHubViewModel.setJobId(job._id)

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
                                    PageImage(image_name = job.page_image, size = 60, horizontal_padding = 5)

                                    Column {
                                        Text(text = job.title, fontSize = 20.sp)

                                        Text(text = job.page, fontSize = 14.sp)

                                        Text(text = job.location, fontSize = 14.sp)

                                        Text(text = job.workplace_type, fontSize = 14.sp)
                                    }

                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }

                            Divider()
                        }
                    }
                }
            }
        }
    }
}