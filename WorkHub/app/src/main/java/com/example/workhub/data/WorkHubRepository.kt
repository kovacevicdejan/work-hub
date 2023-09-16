package com.example.workhub.data

import android.content.Context
import android.util.Log
import com.example.workhub.data.WorkHubRepository.Companion.LOGGED_USER_KEY
import com.example.workhub.data.retrofit.WorkHubApi
import com.example.workhub.data.retrofit.models.Credentials
import com.example.workhub.data.retrofit.models.User
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
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

    suspend fun register(user: User) {
        workHubApi.register(user = user)
    }

    suspend fun login(credentials: Credentials): String {
        return workHubApi.login(credentials = credentials)
    }

    suspend fun getUserByEmail(email: String): User {
        return workHubApi.getUserByEmail(email = email)
    }
}
