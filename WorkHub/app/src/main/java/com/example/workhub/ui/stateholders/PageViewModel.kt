package com.example.workhub.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.PageRepository
import com.example.workhub.data.repository.PostRepository
import com.example.workhub.data.retrofit.models.Follower
import com.example.workhub.data.retrofit.models.Invitation
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PageUiState(
    val page: Page?,
    val posts: List<Post>
)

@HiltViewModel
class PageViewModel @Inject constructor(
    private val pageRepository: PageRepository,
    private val postRepository: PostRepository
) : BaseViewModel<PageEvent>() {
    private val _uiState = MutableStateFlow(
        PageUiState(
            page = null,
            posts = emptyList()
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getPage(name: String) = viewModelScope.launch {
        val page = pageRepository.getPageByName(name = name)
        _uiState.update { it.copy(page = page) }

        val posts: List<Post> = postRepository.getPagePosts(uiState.value.page?.name ?: "")
        _uiState.update { it.copy(posts = posts) }
    }

    fun follow(user: String) = viewModelScope.launch {
        uiState.value.page?.let { pageRepository.follow(user = user, page = it.name) }

        if(uiState.value.page != null) {
            uiState.value.page!!.followers =
                uiState.value.page!!.followers.plus(Follower(user = user))
            _uiState.update { it.copy(page = uiState.value.page) }
        }

        sendEvent(PageEvent.FollowPageEvent)
    }
}