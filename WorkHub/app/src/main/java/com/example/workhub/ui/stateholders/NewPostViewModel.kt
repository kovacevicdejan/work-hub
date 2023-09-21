package com.example.workhub.ui.stateholders

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.ImageRepository
import com.example.workhub.data.repository.PostRepository
import com.example.workhub.data.retrofit.models.Option
import com.example.workhub.data.retrofit.models.User
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

data class NewPostUiState(
    val post_type: String,
    val post_text: String,
    val job_title: String,
    val page_name: String,
    val option: String,
    val option_list: List<String>,
    val image_uri: Uri?
)

@HiltViewModel
class NewPostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val imageRepository: ImageRepository
) : BaseViewModel<PostEvent>() {
    private val _uiState = MutableStateFlow(
        NewPostUiState(
            post_type = "Classic",
            post_text = "",
            job_title = "",
            page_name = "",
            option = "",
            option_list = emptyList(),
            image_uri = null
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setPostType(post_type: String) {
        _uiState.update { it.copy(post_type = post_type) }
    }

    fun setPostText(post_text: String) {
        _uiState.update { it.copy(post_text = post_text) }
    }

    fun setJobTitle(job_title: String) {
        _uiState.update { it.copy(job_title = job_title) }
    }

    fun setPageName(page_name: String) {
        _uiState.update { it.copy(page_name = page_name) }
    }

    fun setOption(option: String) {
        _uiState.update { it.copy(option = option) }
    }

    fun addOption(option: String) {
        _uiState.update { it.copy(option_list = uiState.value.option_list.plus(option)) }
    }

    fun getOptionsText(): String {
        var optionsText = ""

        for(option in uiState.value.option_list) {
            optionsText = "$optionsText$option   "
        }
        return optionsText
    }

    fun setImageUri(image_uri: Uri) {
        _uiState.update { it.copy(image_uri = image_uri) }
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

    fun removeImage() {
        _uiState.update { it.copy(image_uri = null) }
    }

    fun post(
        context: Context,
        post_creator: String,
        creator_type: Int
    ) = viewModelScope.launch {
        var imageName = ""

        if(uiState.value.image_uri != null) {
            val file = uiState.value.image_uri?.let {
                createFileFromImageUri(
                    context = context,
                    imageUri = it,
                    newFileName = "image"
                )
            }

            if(file != null) {
                imageRepository.uploadImage(file = file)
                imageName = file.name
            }
        }

        var options: List<Option> = emptyList()

        for(option in uiState.value.option_list) {
            options = options.plus(Option(option, emptyList()))
        }

        postRepository.newPost(
            post_type = uiState.value.post_type,
            creator_type = creator_type,
            creator = post_creator,
            post_text = uiState.value.post_text,
            post_image = imageName,
            job_title = uiState.value.job_title,
            page_name = uiState.value.page_name,
            options = options
        )

        sendEvent(PostEvent.NewPostEvent)
    }
}