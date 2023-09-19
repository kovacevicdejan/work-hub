package com.example.workhub.ui.stateholders

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.ImageRepository
import com.example.workhub.data.repository.PageRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.requests.CreatePageRequest
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

data class CreatePageUiState(
    val name: String,
    val headline: String,
    val industry: String,
    val location: String,
    val image_uri: Uri?,
    val about: String,
    val website: String
)

@HiltViewModel
class CreatePageViewModel @Inject constructor(
    private val pageRepository: PageRepository,
    private val imageRepository: ImageRepository
) : BaseViewModel<PageEvent>() {
    private val _uiState = MutableStateFlow(
        CreatePageUiState(
            name = "",
            headline = "",
            industry = "",
            location = "",
            image_uri = null,
            about = "",
            website = ""
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun setHeadline(headline: String) {
        _uiState.update { it.copy(headline = headline) }
    }

    fun setIndustry(industry: String) {
        _uiState.update { it.copy(industry = industry) }
    }

    fun setLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun setImageUri(image_uri: Uri) {
        _uiState.update { it.copy(image_uri = image_uri) }
    }

    fun setAbout(about: String) {
        _uiState.update { it.copy(about = about) }
    }

    fun setWebsite(website: String) {
        _uiState.update { it.copy(website = website) }
    }

    fun createPage(context: Context, admin: String) = viewModelScope.launch {
        val file = uiState.value.image_uri?.let {
            createFileFromImageUri(
                context = context,
                imageUri = it,
                newFileName = "image"
            )
        }

        if(file != null)
            imageRepository.uploadImage(file = file)

        val createPageRequest = CreatePageRequest(
            name = uiState.value.name,
            headline = uiState.value.headline,
            industry = uiState.value.industry,
            location = uiState.value.location,
            profile_image = file?.name ?: "",
            about = uiState.value.about,
            website = uiState.value.website,
            admin = admin
        )

        pageRepository.createPage(createPageRequest = createPageRequest)
        sendEvent(PageEvent.CreatePageEvent)
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