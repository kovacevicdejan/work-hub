package com.example.workhub.ui.elements.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.workhub.ui.stateholders.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workhub.R

@Composable
fun LoginScreen(
    workHubViewModel: WorkHubViewModel, authViewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by authViewModel.uiState.collectAsState()
    val context = LocalContext.current

    OnEvent(authViewModel.event) {
        when (it) {
            LoginEvent.LoginSuccess -> {
                workHubViewModel.setCurrUser(uiState.email)
            }

            LoginEvent.LoginFailure -> Toast.makeText(
                context,
                "Invalid credentials!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.programming),
            contentDescription = "codehub"
            ,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { authViewModel.setEmail(it) },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { authViewModel.setPassword(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    authViewModel.login()
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Login", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString("Sign up here"),
            onClick = { },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                color = if(isSystemInDarkTheme()) Color.White else Color.Black
            )
        )
    }
}