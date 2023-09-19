package com.example.workhub.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.PageRepository
import com.example.workhub.data.retrofit.models.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PageUiState(
    val page: Page?,
)

@HiltViewModel
class PageViewModel @Inject constructor(
    private val pageRepository: PageRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        PageUiState(
            page = null
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getPage(name: String) = viewModelScope.launch {
        val page = pageRepository.getPageByName(name = name)
        _uiState.update { it.copy(page = page) }
    }
}