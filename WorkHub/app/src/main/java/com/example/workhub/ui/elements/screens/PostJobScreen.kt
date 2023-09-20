package com.example.workhub.ui.elements.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.workhub.R
import com.example.workhub.ui.stateholders.PostJobViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostJobScreen(
    workHubViewModel: WorkHubViewModel,
    postJobViewModel: PostJobViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val postJobUiState by postJobViewModel.uiState.collectAsState()
    var state by remember { mutableStateOf(true) }
    val workplaceTypes = arrayOf("Office", "Remote", "Hybrid")
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val year: Int
    val month: Int
    val day: Int
    val calendar = Calendar.getInstance()
    val date = remember { mutableStateOf("") }

    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog = DatePickerDialog(
        context,
        if(isSystemInDarkTheme()) R.style.DatePickerDarkTheme else R.style.DatePickerLightTheme,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            date.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, year, month, day
    )

    Card(modifier = Modifier.padding(10.dp), backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE)) {
        Column {
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "New job",
                            modifier = Modifier.weight(1f),
                            fontSize = 30.sp
                        )

                        Button(
                            onClick = {
                                postJobViewModel.postJob(page = uiState.page)
                            }
                        ) {
                            Text(text = "Post", color = Color.White, fontSize = 20.sp)
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = postJobUiState.title,
                            onValueChange = { postJobViewModel.setTitle(it) },
                            label = {Text(text = "Job title")}
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Workplace type: ",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        Box(modifier = Modifier.weight(2f)) {
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = {
                                    expanded = !expanded
                                }
                            ) {
                                TextField(
                                    value = postJobUiState.workplace_type,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expanded
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    workplaceTypes.forEach { item ->
                                        DropdownMenuItem(
                                            onClick = {
                                                postJobViewModel.setWorkplaceType(item)
                                                expanded = false
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
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = postJobUiState.location,
                            onValueChange = { postJobViewModel.setLocation(it) },
                            label = {Text(text = "Job location")}
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = postJobUiState.level,
                            onValueChange = { postJobViewModel.setLevel(it) },
                            label = {Text(text = "Job level")}
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Job type: ",
                            modifier = Modifier.weight(1.2f),
                            fontSize = 20.sp
                        )

                        RadioButton(
                            selected = state,
                            onClick = { postJobViewModel.setJobType(true) },
                        )

                        Text(
                            text = "Internship",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        RadioButton(
                            selected = !state,
                            onClick = { postJobViewModel.setJobType(false) }
                        )

                        Text(
                            text = "Full-time",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )
                    }
                }

                item {
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                        OutlinedTextField(
                            value = postJobUiState.description,
                            onValueChange = { postJobViewModel.setDescription(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            label = {Text(text = "Description")}
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = postJobUiState.tech_stack,
                            onValueChange = { postJobViewModel.setTechStack(it) },
                            label = {Text(text = "Tech stack")}
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = postJobUiState.area,
                            onValueChange = { postJobViewModel.setArea(it) },
                            label = {Text(text = "Job areas")}
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Deadline: ${date.value}",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        Button(
                            onClick = {
                                datePickerDialog.show()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}