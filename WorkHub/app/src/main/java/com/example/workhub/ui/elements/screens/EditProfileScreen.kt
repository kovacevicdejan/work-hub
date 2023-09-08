package com.example.workhub.ui.elements.screens

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workhub.R
import com.example.workhub.SnippetViewModel
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditProfileScreen (
    viewModelFromActivity: SnippetViewModel
) {
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

    Card(modifier = Modifier.padding(10.dp), backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE)) {
        Column {
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Edit profile",
                            modifier = Modifier.weight(1f),
                            fontSize = 30.sp
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "First name: ",
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
                            text = "Last Name: ",
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
                            text = "Headline: ",
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
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                        Text(
                            text = "About: ",
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
                            text = "Industry: ",
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
                            text = "Location: ",
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
                            text = "Contact Info: ",
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
                        Spacer(modifier = Modifier.weight(1f))
                        
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Save", color = Color.White, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}