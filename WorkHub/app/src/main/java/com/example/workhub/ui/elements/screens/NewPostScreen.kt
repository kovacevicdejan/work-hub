package com.example.workhub.ui.elements.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workhub.R
import com.example.workhub.SnippetViewModel
import com.example.workhub.ui.elements.theme.Blue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewPostScreen (
    viewModelFromActivity: SnippetViewModel
) {
    var state by remember { mutableStateOf(true) }

    val postTypes = arrayOf("Classic", "New position", "Poll")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by rememberSaveable { mutableStateOf(postTypes[0]) }
    var jobTitle by rememberSaveable { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var optionsList: List<String> by remember { mutableStateOf(listOf()) }
    var option by remember { mutableStateOf("") }

    Card(modifier = Modifier.padding(10.dp), backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE)) {
        Column {
            LazyColumn {
                item {
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "New post",
                            modifier = Modifier.weight(1f),
                            fontSize = 30.sp
                        )

                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Post", color = Color.White, fontSize = 20.sp)
                        }
                    }

                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Visibility: ",
                            modifier = Modifier.weight(1.2f),
                            fontSize = 20.sp
                        )

                        RadioButton(
                            selected = state,
                            onClick = { state = true },
                        )

                        Text(
                            text = "Anyone",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        RadioButton(
                            selected = !state,
                            onClick = { state = false }
                        )

                        Text(
                            text = "Connections",
                            modifier = Modifier.weight(1.5f),
                            fontSize = 20.sp
                        )
                    }

                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Post type: ",
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
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    postTypes.forEach { item ->
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

                    when(selectedText) {
                        "Classic" -> {
                            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                                Text(
                                    text = "Post text: ",
                                    modifier = Modifier.weight(1f),
                                    fontSize = 20.sp
                                )
                            }

                            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                                OutlinedTextField(
                                    value = "",
                                    onValueChange = {},
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                )
                            }

                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Post image: ",
                                    modifier = Modifier.weight(1f),
                                    fontSize = 20.sp
                                )

                                Button(onClick = {}) {
                                    Text(text = "Select image", color = Color.White)
                                }
                            }

                            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                                Image(
                                    painter = painterResource(id = R.drawable.overflowai),
                                    contentDescription = "linkedin",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp)
                                )
                            }
                        }

                        "New position" -> {
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

                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Company/school: ",
                                    modifier = Modifier.weight(1f),
                                    fontSize = 20.sp
                                )

                                OutlinedTextField(
                                    value = company,
                                    onValueChange = { company = it },
                                    modifier = Modifier.weight(2f)
                                )
                            }

                            if(jobTitle != "" && company != "") {
                                Divider()

                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 5.dp
                                    )
                                ) {
                                    Text(
                                        text = "I'm happy to share that I'm starting a new position as $jobTitle at $company!",
                                        fontSize = 20.sp
                                    )
                                }

                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 5.dp
                                    )
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.overflowai),
                                        contentDescription = "linkedin",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 5.dp)
                                    )
                                }
                            }
                        }

                        "Poll" -> {
                            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                                Text(
                                    text = "Post text: ",
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
                                        .height(200.dp)
                                )
                            }

                            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                                Text(
                                    text = "Poll options:   ",
                                    fontSize = 20.sp
                                )

                                for(opt in optionsList) {
                                    Text(
                                        text = "$opt   ",
                                        fontSize = 20.sp,
                                        color = Blue
                                    )
                                }
                            }

                            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                                OutlinedTextField(
                                    value = option,
                                    onValueChange = {option = it},
                                    modifier = Modifier.weight(2.4f).padding(0.dp, 0.dp, 10.dp, 0.dp).weight(3f)
                                )

                                Button(
                                    onClick = {
                                        optionsList = optionsList.plus(option)
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = "Add option", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}