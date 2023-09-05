package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workhub.SnippetViewModel
import com.example.workhub.ui.elements.theme.Shapes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SavedJobsScreen(
    viewModelFromActivity: SnippetViewModel
) {
    val viewModelFromRoute: SnippetViewModel = viewModel()

    val uiStateFromRoute by viewModelFromRoute.uiState.collectAsState()
    val uiStateFromActivity by viewModelFromActivity.uiState.collectAsState()

    Column {
//        Card(
//            modifier = Modifier.padding(10.dp)
//        ) {
//            Column {
//                Row(
//                    modifier = Modifier.padding(10.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Sort jobs by: ",
//                        modifier = Modifier.weight(1f)
//                    )
//
//                    Box(modifier = Modifier.weight(2.5f)) {
//                        ExposedDropdownMenuBox(
//                            expanded = expanded,
//                            onExpandedChange = {
//                                expanded = !expanded
//                            }
//                        ) {
//                            TextField(
//                                value = selectedText,
//                                onValueChange = {},
//                                readOnly = true,
//                                trailingIcon = {
//                                    ExposedDropdownMenuDefaults.TrailingIcon(
//                                        expanded = expanded
//                                    )
//                                },
//                            )
//
//                            ExposedDropdownMenu(
//                                expanded = expanded,
//                                onDismissRequest = { expanded = false }
//                            ) {
//                                sortTypes.forEach { item ->
//                                    DropdownMenuItem(
//                                        onClick = {
//                                            selectedText = item
//                                            expanded = false
//                                        }
//                                    ) {
//                                        Text(text = item)
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                Row(
//                    modifier = Modifier.padding(10.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Box(modifier = Modifier
//                        .weight(1f)
//                        .padding(0.dp, 0.dp, 5.dp, 0.dp)) {
//                        ExposedDropdownMenuBox(
//                            expanded = expanded1,
//                            onExpandedChange = {
//                                expanded1 = !expanded1
//                            }
//                        ) {
//                            TextField(
//                                value = selectedText1,
//                                onValueChange = {},
//                                readOnly = true,
//                                trailingIcon = {
//                                    ExposedDropdownMenuDefaults.TrailingIcon(
//                                        expanded = expanded1
//                                    )
//                                },
//                            )
//
//                            ExposedDropdownMenu(
//                                expanded = expanded1,
//                                onDismissRequest = { expanded1 = false }
//                            ) {
//                                sortTypes.forEach { item ->
//                                    DropdownMenuItem(
//                                        onClick = {
//                                            selectedText1 = item
//                                            expanded1 = false
//                                        }
//                                    ) {
//                                        Text(text = item)
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    Box(modifier = Modifier
//                        .weight(1f)
//                        .padding(5.dp, 0.dp, 0.dp, 0.dp)) {
//                        ExposedDropdownMenuBox(
//                            expanded = expanded2,
//                            onExpandedChange = {
//                                expanded2 = !expanded2
//                            }
//                        ) {
//                            TextField(
//                                value = selectedText2,
//                                onValueChange = {},
//                                readOnly = true,
//                                trailingIcon = {
//                                    ExposedDropdownMenuDefaults.TrailingIcon(
//                                        expanded = expanded2
//                                    )
//                                },
//                            )
//
//                            ExposedDropdownMenu(
//                                expanded = expanded2,
//                                onDismissRequest = { expanded2 = false }
//                            ) {
//                                sortTypes.forEach { item ->
//                                    DropdownMenuItem(
//                                        onClick = {
//                                            selectedText2 = item
//                                            expanded2 = false
//                                        }
//                                    ) {
//                                        Text(text = item)
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }

        Card(
            modifier = Modifier.padding(vertical = 10.dp),
            backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE),
            shape = Shapes.large
        ) {
            LazyColumn {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = "Saved jobs", fontSize = 24.sp)
                    }

                    Divider()
                }

                for(i in 0..5) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 10.dp)
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

                            Button(onClick = {}, modifier = Modifier.padding(horizontal = 10.dp)) {
                                Text(text = "Remove", color = Color.White)
                            }
                        }

                        Divider()
                    }
                }
            }
        }
    }
}