package com.example.githubuser.ui.screen.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.remote.UserRepository
import com.example.githubuser.data.remote.response.UserListResponseItem
import com.example.githubuser.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<UserListResponseItem>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<UserListResponseItem>>>
        get() = _uiState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    fun getUsers() {
        viewModelScope.launch {
            repository.getUsers()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun getMoreUsers() {
        viewModelScope.launch {
            repository.getMoreUsers()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    @OptIn(FlowPreview::class)
    fun searchUsers(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            _query.debounce(300)
                .collectLatest {
                    repository.searchUser(
                        newQuery
                    )
                        .catch {
                            _uiState.value = UiState.Error(it.message.toString())
                        }
                        .collect {
                            _uiState.value = UiState.Success(it)
                        }
                }
        }
    }
}