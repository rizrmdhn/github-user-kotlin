package com.example.githubuser.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.remote.Result
import com.example.githubuser.data.remote.UserRepository
import com.example.githubuser.data.remote.response.UserListResponse
import com.example.githubuser.data.remote.response.UserListResponseItem
import com.example.githubuser.data.remote.retrofit.ApiConfig
import com.example.githubuser.ui.screen.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<UserListResponseItem>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<UserListResponseItem>>>
        get() = _uiState


    fun getUsers() {
        viewModelScope.launch {
            repository.getUsers()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = it
                }
        }
    }
}