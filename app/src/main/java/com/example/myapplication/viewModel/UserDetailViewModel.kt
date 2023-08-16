package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserDetailViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(UserDetailUiState())
    val uiState = _uiState.asStateFlow()
    fun setEvents(event: UserDetailEvent) {
        when(event) {
            is UserDetailEvent.EditName -> {
                _uiState.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is UserDetailEvent.EditEmail -> {
                _uiState.update {
                    it.copy(
                        email = event.email
                    )
                }
            }
        }
    }

    data class UserDetailUiState(
        val name: String = "",
        val email: String? = null
    )
}

sealed interface UserDetailEvent {
    data class EditName(val name: String) : UserDetailEvent
    data class EditEmail(val email: String) : UserDetailEvent
}