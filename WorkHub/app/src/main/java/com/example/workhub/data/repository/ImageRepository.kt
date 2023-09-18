package com.example.workhub.data.repository

import com.example.workhub.data.retrofit.WorkHubApi
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val workHubApi: WorkHubApi
) {
    suspend fun uploadImage(file: File) {
        workHubApi.uploadImage(
            file = MultipartBody.Part.createFormData(
                name = "image",
                filename = file.name,
                body = file.asRequestBody()
            )
        )
    }
}
