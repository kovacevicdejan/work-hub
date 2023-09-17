package com.example.workhub.data

import android.content.Context
import com.example.workhub.data.retrofit.WorkHubApi
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.data.retrofit.requests.ConnectRequest
import com.example.workhub.data.retrofit.requests.RegistrationRequest
import com.example.workhub.data.retrofit.requests.SignInRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class WorkHubRepository @Inject constructor(
    private val workHubApi: WorkHubApi,
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val SHARED_PREFERENCES_NAME = "work-hub-out-shared-preferences"
        private const val LOGGED_USER_KEY = "logged-user-key"
    }

    fun setLoggedUser(user: String) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(LOGGED_USER_KEY, user)
        editor.apply()
    }

    fun getLoggedUser(): String? {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(LOGGED_USER_KEY, null)
    }

    fun removeLoggedUser() {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(LOGGED_USER_KEY)
        editor.apply()
    }

    suspend fun register(registrationRequest: RegistrationRequest) {
        workHubApi.register(registrationRequest = registrationRequest)
    }

    suspend fun signIn(signInRequest: SignInRequest): String {
        return workHubApi.signIn(signInRequest = signInRequest)
    }

    suspend fun getUserByEmail(email: String): User {
        return workHubApi.getUserByEmail(email = email)
    }

    suspend fun uploadImage(file: File) {
        workHubApi.uploadImage(
            file = MultipartBody.Part.createFormData(
                name = "image",
                filename = file.name,
                body = file.asRequestBody()
            )
        )
    }

    suspend fun getUsersByIndustry(industry: String): List<User> {
        return workHubApi.getUsersByIndustry(industry = industry)
    }

    suspend fun connect(connectRequest: ConnectRequest) {
        return workHubApi.connect(connectRequest = connectRequest)
    }
}
