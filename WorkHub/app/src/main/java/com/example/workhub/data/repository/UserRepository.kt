package com.example.workhub.data.repository

import android.content.Context
import com.example.workhub.data.retrofit.WorkHubApi
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.data.retrofit.requests.ConnectRequest
import com.example.workhub.data.retrofit.requests.RegistrationRequest
import com.example.workhub.data.retrofit.requests.SignInRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserRepository @Inject constructor(
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

    suspend fun getUsersByIndustry(industry: String): List<User> {
        return workHubApi.getUsersByIndustry(industry = industry)
    }

    suspend fun connect(user1: String, user2: String) {
        val connectRequest = ConnectRequest(
            user1 = user1,
            user2 = user2
        )

        return workHubApi.connect(connectRequest = connectRequest)
    }

    suspend fun acceptInvitation(user1: String, user2: String) {
        val connectRequest = ConnectRequest(
            user1 = user1,
            user2 = user2
        )

        return workHubApi.acceptInvitation(connectRequest = connectRequest)
    }

    suspend fun declineInvitation(user1: String, user2: String) {
        val connectRequest = ConnectRequest(
            user1 = user1,
            user2 = user2
        )

        return workHubApi.declineInvitation(connectRequest = connectRequest)
    }

    suspend fun withdrawInvitation(user1: String, user2: String) {
        val connectRequest = ConnectRequest(
            user1 = user1,
            user2 = user2
        )

        return workHubApi.withdrawInvitation(connectRequest = connectRequest)
    }

    suspend fun removeConnection(user1: String, user2: String) {
        val connectRequest = ConnectRequest(
            user1 = user1,
            user2 = user2
        )

        return workHubApi.removeConnection(connectRequest = connectRequest)
    }
}
