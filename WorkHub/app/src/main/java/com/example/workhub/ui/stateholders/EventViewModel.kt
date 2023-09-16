package com.example.workhub.ui.stateholders

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

interface Event

abstract class BaseViewModel<E : Event> : ViewModel() {
    private val _event = Channel<E>()
    val event = _event.receiveAsFlow().shareIn(viewModelScope, SharingStarted.Lazily)
    protected suspend fun sendEvent(event: E) {
        Log.d("print", _event.isClosedForReceive.toString())
        _event.send(event)
    }
    protected fun sendEventSync(event: E) = viewModelScope.launch { _event.send(event) }
}

@Composable
fun <E : Event> OnEvent(event: Flow<E>, onEvent: (E) -> Unit) {
    LaunchedEffect(Unit) {
        event.collect(onEvent)
    }
}

sealed interface LoginEvent : Event {
    object LoginSuccess : LoginEvent
    object LoginFailure: LoginEvent
    data class LoginFailure1(val exception: Throwable) : LoginEvent
}

sealed interface RegistrationEvent : Event {
    object RegistrationSuccess : RegistrationEvent
    data class RegistrationFailure(val exception: Throwable) : RegistrationEvent
}

sealed interface GetUserEvent : Event {
    object GetUserSuccess: GetUserEvent
    object GetUserFailure: GetUserEvent
}