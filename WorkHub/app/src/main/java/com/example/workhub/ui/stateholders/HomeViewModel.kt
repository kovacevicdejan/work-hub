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
    val posts: List<Post>,
    val users: Map<String, User>,
    val pages: Map<String, Page>,
    val timestamp: Long
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository
) : BaseViewModel<ChatEvent>() {
    private val _uiState = MutableStateFlow(
        HomeUiState(
            posts = emptyList(),
            users = emptyMap(),
            pages = emptyMap(),
            timestamp = 0
        )
    )

    val uiState = _uiState.asStateFlow()

//    fun getUsersAndPages(posts: LazyPagingItems<Post>) = viewModelScope.launch {
//        var users: Map<String, User> = emptyMap()
//        var pages: Map<String, Page> = emptyMap()
//
//        for(i in 0 until posts.itemCount) {
//            if ((posts[i]?.creator_type ?: "") == 0) {
//                if (!users.containsKey(posts[i]?.creator ?: "")) {
//                    val user = userRepository.getUserByEmail(posts[i]?.creator ?: "")
//                    users = users.plus(Pair(posts[i]?.creator ?: "", user))
//                }
//            } else {
//                if (!pages.containsKey(posts[i]?.creator ?: "")) {
//                    val page = pageRepository.getPageByName(posts[i]?.creator ?: "")
//                    pages = pages.plus(Pair(posts[i]?.creator ?: "", page))
//                }
//            }
//        }
//
//        _uiState.update { it.copy(users = users) }
//        _uiState.update { it.copy(pages = pages) }
//    }

    fun setTimestamp() {
        _uiState.update { it.copy(timestamp = System.currentTimeMillis()) }
    }

    fun getPosts(email: String) =
        postRepository.getPosts(email = email, timestamp = uiState.value.timestamp).cachedIn(viewModelScope)
}