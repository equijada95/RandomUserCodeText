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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: RandomUserRepository
) : ViewModel() {

    val state: StateFlow<ListState> get() = _state.asStateFlow()

    private val _state = MutableStateFlow(ListState())

    init {
        viewModelScope.launch {
            getUsers()
        }
    }

    private suspend fun getUsers() {
        repository.getUsers().collect { result ->
            when (result) {
                is ApiResult.Success -> {
                    _state.update {
                        it.copy(
                            userList = result.data ?: emptyList(),
                            loading = false,
                            error = ApiResult.ApiError.NO_ERROR,
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
        val error: ApiResult.ApiError = ApiResult.ApiError.NO_ERROR
    )
}