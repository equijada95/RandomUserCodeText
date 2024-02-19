package com.equijada95.randomusercodetext.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.equijada95.domain.model.User
import com.equijada95.domain.repository.RandomUserRepository
import com.equijada95.domain.result.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: RandomUserRepository
) : ViewModel() {

    val state: StateFlow<ListState> get() = _state.asStateFlow()

    private val _state = MutableStateFlow(ListState())

    private val originalUsers = MutableStateFlow(emptyList<User>())

    private var searchText = MutableStateFlow("")

    init {
        viewModelScope.launch {
            getUsers()
        }
    }

    fun search(searchText: String) {
        this.searchText.update { searchText }
        val searchHeros = originalUsers.value.filter { user ->
            user.name.uppercase().contains(searchText.uppercase())
        }
        _state.update { it.copy(userList = searchHeros) }
    }

    private suspend fun getUsers() {
        repository.getUsers().collect { result ->
            when (result) {
                is ApiResult.Success -> {
                    var users = result.data ?: emptyList()
                    if (searchText.value.isNotEmpty()) {
                        users = users.filter { hero ->
                            hero.name.uppercase().contains(searchText.value.uppercase())
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

                else -> Unit // TODO ERROR
            }
        }
    }

    data class ListState (
        val userList: List<User> = emptyList(),
        val loading: Boolean = false,
        val refreshing: Boolean = false,
    )

    sealed class Event {
        class Error(error: ApiResult.ApiError)
    }
}