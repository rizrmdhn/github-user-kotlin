package com.example.githubuser.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.local.entity.FavoriteUserEntity
import com.example.githubuser.data.remote.UserRepository
import com.example.githubuser.data.remote.response.DetailUserResponse
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

    private val _isFavoriteUser = MutableStateFlow(false)
    val isFavoriteUser: MutableStateFlow<Boolean> get() = _isFavoriteUser


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

    fun insertFavoriteUser(favoriteUser: FavoriteUserEntity) {
        viewModelScope.launch {
            if (_isFavoriteUser.value) {
                repository.deleteFavoriteUser(favoriteUser.login)
            } else {
                repository.insertFavoriteUser(favoriteUser)
            }
        }
    }

    fun isFavoriteUser(username: String) {
        viewModelScope.launch {
            repository.isFavoriteUser(username).collect {
                _isFavoriteUser.value = it
            }
        }
    }
}