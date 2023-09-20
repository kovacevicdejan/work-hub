package com.example.workhub.ui.elements.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.workhub.R
import com.example.workhub.data.retrofit.BASE_URL
import com.example.workhub.data.retrofit.models.Post
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.ui.elements.theme.Blue
import com.example.workhub.ui.stateholders.NewPostViewModel
import com.example.workhub.ui.stateholders.PostViewModel
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun Post(
    post: Post,
    workHubViewModel: WorkHubViewModel,
    postViewModel: PostViewModel = hiltViewModel(),
    navController: NavHostController,
    curr_user: String
) {
    val postUiState by postViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        postViewModel.getPostCreator(
            creator = post.creator,
            creator_type = post.creator_type
        )
    }

    Card(modifier = Modifier.padding(start = 5.dp, top = 5.dp, end = 5.dp, bottom = 5.dp), backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE)) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if(postUiState.user != null) {
                    IconButton(
                        onClick = {
                            postUiState.user?.let { workHubViewModel.setUser(it.email) }

                            navController.navigate("Profile") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        ProfileImage(
                            image_name = postUiState.user!!.profile_image,
                            size = 60,
                            vertical_padding = 5,
                            horizontal_padding = 5
                        )
                    }

                    Column {
                        Text(text = postUiState.user!!.firstname + " " + postUiState.user!!.lastname, fontSize = 20.sp)

                        Text(text = postUiState.user!!.headline, fontSize = 12.sp)

                        Text(text = "1d", fontSize = 12.sp)
                    }

                    if (postUiState.user!!.email != curr_user) {
                        Spacer(modifier = Modifier.weight(1f))

                        TextButton(onClick = {}) {
                            Text(text = "+ Connect", color = Color(0xFF0077B5), fontSize = 20.sp)
                        }
                    }
                }
                else if(postUiState.page != null) {
                    IconButton(
                        onClick = {
                            postUiState.page?.let { workHubViewModel.setPage(it.name) }

                            navController.navigate("Page") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        PageImage(
                            image_name = postUiState.page!!.profile_image,
                            size = 60,
                            vertical_padding = 5,
                            horizontal_padding = 5
                        )
                    }

                    Column {
                        Text(text = postUiState.page!!.name, fontSize = 20.sp)

                        Text(text = postUiState.page!!.headline, fontSize = 12.sp)

                        Text(text = "${postUiState.page!!.followers.size} followers", fontSize = 12.sp)
                    }
                }
            }

            when(post.post_type) {
                "Classic" -> {
                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text(
                            text = post.post_text,
                            modifier = Modifier.padding(vertical = 10.dp),
                            fontSize = 16.sp
                        )
                    }

                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        AsyncImage(
                            model = BASE_URL + "image/get_image/" + post.post_image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                "New position" -> {
                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text(
                            text = "I'm happy to share that I'm starting a new position as ${post.job_title} at ${post.page_name}!",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }

                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.overflowai),
                            contentDescription = "linkedin"
                            ,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }

                "Poll" -> {
                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text(
                            text = post.post_text,
                            modifier = Modifier.padding(vertical = 10.dp),
                            fontSize = 16.sp
                        )
                    }

                    Column() {
                        for (option in post.options) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = false,
                                    onClick = {  }
                                )

                                Text(
                                    text = option.text,
                                    fontSize = 20.sp,
                                    color = Blue
                                )

                                Text(
                                    text = "   (${option.voters.size} votes)",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
            
            Row(modifier = Modifier.padding(10.dp)) {
                Icon(
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = null,
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                )
                
                Text(text = " ${post.likes.size}", fontSize = 12.sp)
                
                Spacer(modifier = Modifier.weight(1f))
                
                Text(text = "${post.comments.size} comments", fontSize = 12.sp)
            }
            
            Divider()

            Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "Like", modifier = Modifier.size(20.dp))
                            Text(text = "Like", fontSize = 12.sp)
                        }
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    IconButton(
                        onClick = {
                            navController.navigate("Comments") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = Icons.Default.Email, contentDescription = "Comment", modifier = Modifier.size(20.dp))
                            Text(text = "Comment", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}