package com.example.workhub.ui.elements.screens

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.workhub.ui.stateholders.AuthViewModel
import com.example.workhub.ui.stateholders.OnEvent
import com.example.workhub.ui.stateholders.RegistrationEvent
import com.example.workhub.ui.stateholders.WorkHubViewModel

@Composable
fun RegistrationScreen(
    workHubViewModel: WorkHubViewModel,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by authViewModel.uiState.collectAsState()
    val context = LocalContext.current

    OnEvent(authViewModel.event) {
        when (it) {
            RegistrationEvent.RegistrationSuccess -> {
                workHubViewModel.setCurrUser(uiState.email)
            }
            is RegistrationEvent.RegistrationFailure ->
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
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
                            text = "Registration",
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
                        OutlinedTextField(
                            value = uiState.email,
                            onValueChange = { authViewModel.setEmail(it) },
                            label = { Text("Email") },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = uiState.password,
                            onValueChange = { authViewModel.setPassword(it) },
                            modifier = Modifier.weight(2f),
                            label = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = uiState.firstname,
                            onValueChange = { authViewModel.setFirstname(it) },
                            label = { Text("Firstname") },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = uiState.lastname,
                            onValueChange = { authViewModel.setLastname(it) },
                            label = { Text("Lastname") },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                        OutlinedTextField(
                            value = uiState.about,
                            onValueChange = { authViewModel.setAbout(it) },
                            label = { Text("About") },
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
                        OutlinedTextField(
                            value = uiState.headline,
                            onValueChange = { authViewModel.setHeadline(it) },
                            label = { Text("Headline") },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = uiState.location,
                            onValueChange = { authViewModel.setLocation(it) },
                            label = { Text("Location") },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = uiState.phone_number,
                            onValueChange = { authViewModel.setPhoneNumber(it) },
                            label = { Text("Phone number") },
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = uiState.industry,
                            onValueChange = { authViewModel.setIndustry(it) },
                            label = { Text("Industry") },
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

                        Button(
                            onClick = {
                                authViewModel.register()
                            }
                        ) {
                            Text(text = "Register", color = Color.White, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}