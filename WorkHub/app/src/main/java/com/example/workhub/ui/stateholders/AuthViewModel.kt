package com.example.workhub.ui.stateholders

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.WorkHubRepository
import com.example.workhub.data.retrofit.requests.RegistrationRequest
import com.example.workhub.data.retrofit.requests.SignInRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class AuthUiState(
    val email: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val image_uri: Uri?,
    val about: String,
    val headline: String,
    val location: String,
    val phone_number: String,
    val industry: String,
    val valid_user: Boolean
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val workHubRepository: WorkHubRepository,
) : BaseViewModel<Event>() {
    private val _uiState = MutableStateFlow(
        AuthUiState(
            email = "",
            password = "",
            firstname = "",
            lastname = "",
            image_uri = null,
            about = "",
            headline = "",
            location = "",
            phone_number = "",
            industry = "",
            valid_user = false
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun setPassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun setFirstname(firstname: String) {
        _uiState.update { it.copy(firstname = firstname) }
    }

    fun setLastname(lastname: String) {
        _uiState.update { it.copy(lastname = lastname) }
    }

    fun setImageUri(image_uri: Uri) {
        _uiState.update { it.copy(image_uri = image_uri) }
    }

    fun setAbout(about: String) {
        _uiState.update { it.copy(about = about) }
    }

    fun setHeadline(headline: String) {
        _uiState.update { it.copy(headline = headline) }
    }

    fun setLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun setPhoneNumber(phone_number: String) {
        _uiState.update { it.copy(phone_number = phone_number) }
    }

    fun setIndustry(industry: String) {
        _uiState.update { it.copy(industry = industry) }
    }

    fun register(context: Context) = viewModelScope.launch {
        val file = uiState.value.image_uri?.let {
            createFileFromImageUri(
                context = context,
                imageUri = it,
                newFileName = "image"
            )
        }

        if(file != null)
            workHubRepository.uploadImage(file = file)

        val registrationRequest = RegistrationRequest(
            email = uiState.value.email,
            password = uiState.value.password,
            firstname = uiState.value.firstname,
            lastname = uiState.value.lastname,
            profile_image = file?.name ?: "",
            about = uiState.value.about,
            headline = uiState.value.headline,
            location = uiState.value.location,
            phone_number = uiState.value.phone_number,
            industry = uiState.value.industry,
        )

        workHubRepository.register(registrationRequest = registrationRequest)
        workHubRepository.setLoggedUser(uiState.value.email)
        sendEvent(RegistrationEvent.RegistrationSuccess)
    }

    fun signIn() = viewModelScope.launch {
        val signInRequest = SignInRequest(
            email = uiState.value.email,
            password = uiState.value.password
        )

        val message = workHubRepository.signIn(signInRequest = signInRequest)

        if(message == "Sign In successful!") {
            workHubRepository.setLoggedUser(uiState.value.email)
            sendEvent(SignInEvent.SignInSuccess)
        }
        else
            sendEvent(SignInEvent.SignInFailure)
    }

    fun createFileFromImageUri(context: Context, imageUri: Uri, newFileName: String): File? {
        try {
            val contentResolver: ContentResolver = context.contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(imageUri)

            if (inputStream != null) {
                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                val fileName = "$newFileName-$timeStamp.jpg"
                val file = File(context.cacheDir, fileName)
                val outputStream = FileOutputStream(file)
                val buffer = ByteArray(1024)
                var bytesRead: Int

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }

                inputStream.close()
                outputStream.close()
                return file
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}