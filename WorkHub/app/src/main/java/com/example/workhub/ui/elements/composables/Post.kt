package com.example.workhub.ui.elements.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.workhub.R
import com.example.workhub.data.retrofit.BASE_URL
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.Post
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun Post(
    post: Post,
    user: User?,
    page: Page?,
    workHubViewModel: WorkHubViewModel,
    navController: NavHostController
) {
    val uiState by workHubViewModel.uiState.collectAsState()

    Card(modifier = Modifier.padding(start = 5.dp, top = 5.dp, end = 5.dp, bottom = 5.dp), backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE)) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if(user != null) {
                    IconButton(
                        onClick = {
                            user.let { workHubViewModel.setUser(it.email) }

                            navController.navigate("Profile") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        ProfileImage(
                            image_name = user.profile_image,
                            size = 60,
                            vertical_padding = 5,
                            horizontal_padding = 5
                        )
                    }

                    Column {
                        Text(text = user.firstname + " " + user.lastname, fontSize = 20.sp)

                        Text(text = user.headline, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }
                else if(page != null) {
                    IconButton(
                        onClick = {
                            page.let { workHubViewModel.setPage(it.name) }

                            navController.navigate("Page") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        PageImage(
                            image_name = page.profile_image,
                            size = 60,
                            vertical_padding = 5,
                            horizontal_padding = 5
                        )
                    }

                    Column {
                        Text(text = page.name, fontSize = 20.sp)

                        Text(text = page.headline, fontSize = 12.sp)

                        Text(text = "${page.followers.size} followers", fontSize = 12.sp)
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
            }
            
            Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    IconButton(
                        onClick = {
                            workHubViewModel.setPostId(post_id = post._id)

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
                
                Spacer(modifier = Modifier.weight(2f))

                Text(text = "${post.comments.size} comments", fontSize = 12.sp, modifier = Modifier.weight(1f))
            }
        }
    }
}