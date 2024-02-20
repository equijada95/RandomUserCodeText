package com.equijada95.randomusercodetext.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.equijada95.domain.model.User
import com.equijada95.domain.repository.RandomUserRepository
import com.equijada95.domain.result.ApiResult
import com.equijada95.randomusercodetext.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: RandomUserRepository
) : ViewModel() {

    val state: StateFlow<ListState> get() = _state.asStateFlow()
    private val _state = MutableStateFlow(ListState())

    private val _event = Channel<Event>()
    val event = _event.receiveAsFlow()

    private val originalUsers = MutableStateFlow(emptyList<User>())

    private var searchText = MutableStateFlow("")

    init {
        viewModelScope.launch {
            getUsers()
        }
    }

    fun search(searchText: String) {
        this.searchText.update { searchText }
        val searchUsers = originalUsers.value.filter { user ->
            user.name.uppercase().contains(searchText.uppercase()) || user.email.uppercase().contains(searchText.uppercase())
        }
        _state.update { it.copy(userList = searchUsers) }
    }

    private suspend fun getUsers() {
        repository.getUsers(20).collect { result ->
            when (result) {
                is ApiResult.Success -> {
                    var users = result.data ?: emptyList()
                    if (searchText.value.isNotEmpty()) { // ESTO ES PARA QUE SE CARGUE SI REFRESCA BUSCANDO
                        users = users.filter { user ->
                            user.name.uppercase().contains(searchText.value.uppercase()) || user.email.uppercase().contains(searchText.value.uppercase())
                        }
                    } else {
                        originalUsers.update { users }
                    }
                    _state.update {
                        it.copy(
                            userList = users,
                            loading = false,
                            refreshing = false
                        )
                    }
                }

                is ApiResult.Error -> {
                    result.error?.let { handleError(it) }
                }
                else -> Unit
            }
        }
    }

    private suspend fun handleError(error: ApiResult.ApiError) {
        val messageId = when(error) {
            ApiResult.ApiError.SERVER_ERROR -> R.string.error_server
            ApiResult.ApiError.NO_CONNECTION_ERROR -> R.string.error_no_connection
        }
        _event.send(Event.Error(messageId))
    }

    data class ListState (
        val userList: List<User> = emptyList(),
        val loading: Boolean = true,
        val refreshing: Boolean = false,
    )

    sealed class Event(val messageId: Int) {
        class Error(messageId: Int): Event(messageId)
    }
}