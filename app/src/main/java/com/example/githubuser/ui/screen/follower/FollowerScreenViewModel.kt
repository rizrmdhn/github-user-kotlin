package com.example.githubuser.ui.screen.follower

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.remote.UserRepository
import com.example.githubuser.data.remote.response.UserListResponseItem
import com.example.githubuser.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FollowerScreenViewModel(
    private val repository: UserRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<UserListResponseItem>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<UserListResponseItem>>>
        get() = _uiState


    fun getFollowers(username: String) {
        viewModelScope.launch {
            repository.getUserDetailsFollower(username)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}