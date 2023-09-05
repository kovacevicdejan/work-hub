package com.example.workhub.ui.elements.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workhub.R

@Composable
fun Post(last: Boolean, navController: NavHostController) {
    Card(modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = if (last) 10.dp else 0.dp), backgroundColor = if(isSystemInDarkTheme()) Color(0xFF202020) else Color(0xFFEEEEEE)) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                )

                Column {
                    Text(text = "Pera Peric", fontSize = 20.sp)

                    Text(text = "Backend engineer", fontSize = 10.sp)

                    Text(text = "1d", fontSize = 10.sp)
                }

                Spacer(modifier = Modifier.weight(1f))

                TextButton(onClick = {}) {
                    Text(text = "+ Connect", color = Color(0xFF0077B5), fontSize = 20.sp)
                }
            }

            Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                Text(text = "Below doc contains all useful shortcuts for quick navigation in VS Code and also extensions which will increase your productivity as well as beautify your code.\n" +
                        "\n" +
                        "Save this pdf and Repost if you find this helpful.\n" +
                        "\n" +
                        "Learn programming from W3Schools.com\n" +
                        "\n" +
                        "Follow Ajit Kumar Gupta for more.\n" +
                        "\n" +
                        "Credit - JSMastery , Maheshpal.", modifier = Modifier.padding(vertical = 10.dp), fontSize = 16.sp)
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
            
            Row(modifier = Modifier.padding(10.dp)) {
                Icon(
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = null,
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                )
                
                Text(text = " 1234", fontSize = 12.sp)
                
                Spacer(modifier = Modifier.weight(1f))
                
                Text(text = "27 comments", fontSize = 12.sp)
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
//                            popUpTo(JobsDestination.route) {
//                                saveState = true
//                                inclusive = false
//                            }
                            }
                        }
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = Icons.Default.Email, contentDescription = "Comment", modifier = Modifier.size(20.dp))
                            Text(text = "Comment", fontSize = 12.sp)
                        }
                    }
                }

//                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Repost", modifier = Modifier.size(20.dp))
//                            Text(text = "Repost", fontSize = 12.sp)
//                        }
//                    }
//                }
            }
        }
    }
}