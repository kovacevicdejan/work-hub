package com.example.workhub.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.text.method.TextKeyListener.clear
import androidx.annotation.RequiresApi
import com.example.workhub.data.retrofit.WorkHubApi
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.data.retrofit.requests.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val workHubApi: WorkHubApi,
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val SHARED_PREFERENCES_NAME = "work-hub-out-shared-preferences"
        private const val SIGNED_USER_KEY = "signed-user-key"
    }

    fun setLoggedUser(user: String) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(SIGNED_USER_KEY, user)
        editor.apply()
    }

    fun getLoggedUser(): String? {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(SIGNED_USER_KEY, null)
    }

    fun removeLoggedUser() {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(SIGNED_USER_KEY)
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

    suspend fun getRecommendedUsers(email: String): List<User> {
        return workHubApi.getRecommendedUsers(email = email)
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

    suspend fun editProfile(
        email: String,
        firstname: String,
        lastname: String,
        about: String,
        headline: String,
        location: String,
        interests: String,
        phone_number: String
    ) {
        val editProfileRequest = EditProfileRequest(
            email = email,
            firstname = firstname,
            lastname = lastname,
            about = about,
            headline = headline,
            location = location,
            interests = interests,
            phone_number = phone_number,
        )

        workHubApi.editProfile(editProfileRequest = editProfileRequest)
    }

    suspend fun addSKill(
        user: String,
        skill: String
    ) {
        val addSkillRequest = AddSkillRequest(
            user = user,
            skill = skill
        )

        workHubApi.addSkill(addSkillRequest = addSkillRequest)
    }

    suspend fun addExperience(addExperienceRequest: AddExperienceRequest) {
        workHubApi.addExperience(addExperienceRequest = addExperienceRequest)
    }

    suspend fun getConnections(user: String): List<User> {
        return workHubApi.getConnections(user = user)
    }

    suspend fun searchUsers(keyword: String): List<User> {
        return workHubApi.searchUsers(keyword = keyword)
    }
}
