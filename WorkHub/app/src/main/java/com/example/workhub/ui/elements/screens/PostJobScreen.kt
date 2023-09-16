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
import com.example.workhub.R
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostJobScreen (
) {
    var state by remember { mutableStateOf(true) }

    val workplaceTypes = arrayOf("Office", "Remote", "Hybrid")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by rememberSaveable { mutableStateOf(workplaceTypes[0]) }
    val companies = arrayOf("Microsoft", "TomTom", "Nutanix")
    var expanded1 by remember { mutableStateOf(false) }
    var selectedText1 by rememberSaveable { mutableStateOf(companies[0]) }
    var jobTitle by rememberSaveable { mutableStateOf("") }

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

//    industry, expirience level internship medior senior junior entry level director executive
//    Computer & Network Security
//    Computer Software
//            Information Technology & Services
//
//            Market Research
//            Marketing & Advertising
//    Newspapers
//    Online Media
//            Printing
//    Public Relations & Communications
//            Publishing
//    Translation & Localization
//    Writing & Editing
//
//    Computer Hardware
//            Computer Networking
//            Telecommunications
//    Biotechnology
//    Medicine
//    Pharmacy
//    Banking/management/financy
//    Education/research

    Card(modifier = Modifier.padding(10.dp), backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE)) {
        Column {
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "New post",
                            modifier = Modifier.weight(1f),
                            fontSize = 30.sp
                        )

                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Post", color = Color.White, fontSize = 20.sp)
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Job title: ",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        OutlinedTextField(
                            value = jobTitle,
                            onValueChange = { jobTitle = it },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Company: ",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        Box(modifier = Modifier.weight(2f)) {
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
                                    modifier = Modifier.fillMaxWidth()
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded1,
                                    onDismissRequest = { expanded1 = false }
                                ) {
                                    companies.forEach { item ->
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
                                    value = selectedText,
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
                                                selectedText = item
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
                        Text(
                            text = "Job location: ",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        OutlinedTextField(
                            value = jobTitle,
                            onValueChange = { jobTitle = it },
                            modifier = Modifier.weight(2f)
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
                            onClick = { state = true },
                        )

                        Text(
                            text = "Internship",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        RadioButton(
                            selected = !state,
                            onClick = { state = false }
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
                        Text(
                            text = "Job description: ",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )
                    }

                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
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