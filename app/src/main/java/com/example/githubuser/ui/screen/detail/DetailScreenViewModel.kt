package com.example.githubuser.ui.screen.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.remote.UserRepository
import com.example.githubuser.data.remote.response.DetailUserResponse
import com.example.githubuser.data.remote.response.UserListResponseItem
import com.example.githubuser.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    private val repository: UserRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<DetailUserResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DetailUserResponse>>
        get() = _uiState

    private val _uiStateFollower: MutableStateFlow<UiState<List<UserListResponseItem>>> =
        MutableStateFlow(UiState.Loading)
    val uiStateFollower: StateFlow<UiState<List<UserListResponseItem>>>
        get() = _uiStateFollower

    private val _uiStateFollowing: MutableStateFlow<UiState<List<UserListResponseItem>>> =
        MutableStateFlow(UiState.Loading)
    val uiStateFollowing: StateFlow<UiState<List<UserListResponseItem>>>
        get() = _uiStateFollowing


    fun getUserDetails(username: String) {
        viewModelScope.launch {
            repository.getUserDetails(username)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    if (it != null) {
                        _uiState.value = UiState.Success(it)

                    }
                }

        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            repository.getUserDetailsFollower(username)
                .catch {
                    _uiStateFollower.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiStateFollower.value = UiState.Success(it)
                }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            repository.getUserDetailsFollowing(username)
                .catch {
                    _uiStateFollowing.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiStateFollowing.value = UiState.Success(it)
                }
        }
    }

}