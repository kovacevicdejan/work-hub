package com.example.workhub.ui.elements.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.workhub.R
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.stateholders.NewPostViewModel
import com.example.workhub.ui.stateholders.OnEvent
import com.example.workhub.ui.stateholders.PostEvent
import com.example.workhub.ui.stateholders.WorkHubViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewPostScreen(
    workHubViewModel: WorkHubViewModel,
    newPostViewModel: NewPostViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()
    val newPostUiState by newPostViewModel.uiState.collectAsState()
    val postTypes = arrayOf("Classic", "New position", "Poll")
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {
            it?.let { uri ->
                newPostViewModel.setImageUri(image_uri = uri)

                val file = newPostViewModel.createFileFromImageUri(
                    context = context,
                    imageUri = uri,
                    newFileName = "image"
                )
            }
        }

    OnEvent(newPostViewModel.event) {
        when(it) {
            PostEvent.NewPostEvent -> {
                if(uiState.creator_type == 0) {
                    workHubViewModel.setUser(uiState.post_creator)

                    navController.navigate("User Posts") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                else {
                    navController.navigate("Page") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }

    Card(
        modifier = Modifier.padding(10.dp),
        backgroundColor = if (isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE)
    ) {
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

                        Button(
                            onClick = {
                                newPostViewModel.post(
                                    context = context,
                                    post_creator = uiState.post_creator,
                                    creator_type = uiState.creator_type
                                )
                            }
                        ) {
                            Text(text = "Post", color = Color.White, fontSize = 20.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Visibility: ",
                            modifier = Modifier.weight(1.2f),
                            fontSize = 20.sp
                        )

                        RadioButton(
                            selected = newPostUiState.visibility,
                            onClick = { newPostViewModel.setVisibility(true) }
                        )

                        Text(
                            text = "Anyone",
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp
                        )

                        RadioButton(
                            selected = !newPostUiState.visibility,
                            onClick = { newPostViewModel.setVisibility(false) }
                        )

                        Text(
                            text = if(uiState.creator_type == 0) "Connections" else "Followers",
                            modifier = Modifier.weight(1.5f),
                            fontSize = 20.sp
                        )
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
                                    value = newPostUiState.post_type,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expanded
                                        )
                                    },
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    postTypes.forEach { item ->
                                        DropdownMenuItem(
                                            onClick = {
                                                newPostViewModel.setPostType(item)
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

                    when (newPostUiState.post_type) {
                        "Classic" -> {
                            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                                OutlinedTextField(
                                    value = newPostUiState.post_text,
                                    onValueChange = {newPostViewModel.setPostText(it)},
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp),
                                    label = {Text(text = "Post text")}
                                )
                            }

                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Post image: ",
                                    modifier = Modifier.weight(1f),
                                    fontSize = 18.sp
                                )

                                Button(
                                    onClick = {
                                        launcher.launch(arrayOf("image/*"))
                                    },
                                    modifier = Modifier.padding(5.dp)
                                ) {
                                    Text(text = "Select Image", color = Color.White)
                                }

                                Button(
                                    onClick = {
                                        newPostViewModel.removeImage()
                                    },
                                    modifier = Modifier.padding(5.dp)
                                ) {
                                    Text(text = "Remove Image", color = Color.White)
                                }
                            }

                            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                                AsyncImage(
                                    model = newPostUiState.image_uri,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        "New position" -> {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = newPostUiState.job_title,
                                    onValueChange = { newPostViewModel.setJobTitle(it) },
                                    label = {Text(text = "Job title")},
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = newPostUiState.page_name,
                                    onValueChange = { newPostViewModel.setPageName(it) },
                                    label = {Text(text = "Company or school")},
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            if (newPostUiState.job_title != "" && newPostUiState.page_name != "") {
                                Divider()

                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 5.dp
                                    )
                                ) {
                                    Text(
                                        text = "I'm happy to share that I'm starting a new position as ${newPostUiState.job_title} at ${newPostUiState.page_name}!",
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
                                OutlinedTextField(
                                    value = newPostUiState.post_text,
                                    onValueChange = {newPostViewModel.setPostText(it)},
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    label = {Text(text = "Post text")}
                                )
                            }

                            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                                Text(
                                    text = "Poll options:   ",
                                    fontSize = 20.sp
                                )

                                Text(
                                    text = newPostViewModel.getOptionsText(),
                                    fontSize = 20.sp,
                                    color = Blue,
                                    maxLines = 5
                                )
                            }

                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = newPostUiState.option,
                                    onValueChange = { newPostViewModel.setOption(it) },
                                    modifier = Modifier
                                        .weight(2.4f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                        .weight(3f)
                                )

                                Button(
                                    onClick = {
                                        newPostViewModel.addOption(newPostUiState.option)
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