package com.example.workhub.ui.stateholders

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.example.workhub.data.repository.PageRepository
import com.example.workhub.data.repository.PostRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.Post
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.utils.PostUserPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.HashMap

data class HomeUiState(
    val timestamp: Long,
    val isRefreshing: Boolean
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository
) : BaseViewModel<ChatEvent>() {
    private val _uiState = MutableStateFlow(
        HomeUiState(
            timestamp = 0,
            isRefreshing = false
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setTimestamp() {
        _uiState.update { it.copy(timestamp = System.currentTimeMillis()) }
    }

    fun getPosts(email: String) =
        postRepository.getPosts(email = email, timestamp = uiState.value.timestamp).cachedIn(viewModelScope)
}