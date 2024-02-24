package com.example.githubuser.ui.common

import com.example.githubuser.data.remote.response.DetailUserResponse

sealed class UiState<out T : Any?> {

    object Loading : UiState<Nothing>()

    data class Success<out T : Any>(val data: T) : UiState<T>()

    data class Error(val errorMessage: String) : UiState<Nothing>()

}