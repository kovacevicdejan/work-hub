package com.example.workhub.ui.stateholders

import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.PageRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.Follower
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostUiState(
    val user: User?,
    val page: Page?
)

@HiltViewModel
class PostViewModel @Inject constructor(
    private val pageRepository: PageRepository,
    private val userRepository: UserRepository
) : BaseViewModel<PageEvent>() {
    private val _uiState = MutableStateFlow(
        PostUiState(
            user = null,
            page = null
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getPostCreator(creator: String, creator_type: Int) = viewModelScope.launch {
        if(creator_type == 0) {
            val user = userRepository.getUserByEmail(email = creator)
            _uiState.update { it.copy(user = user) }
        }
        else {
            val page = pageRepository.getPageByName(name = creator)
            _uiState.update { it.copy(page = page) }
        }
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