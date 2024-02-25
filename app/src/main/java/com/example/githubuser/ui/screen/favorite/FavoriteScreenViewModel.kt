package com.example.githubuser.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.local.entity.FavoriteUserEntity
import com.example.githubuser.data.remote.UserRepository
import com.example.githubuser.ui.common.UiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FavoriteUserEntity>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: MutableStateFlow<UiState<List<FavoriteUserEntity>>>
        get() = _uiState

    private val _query = MutableStateFlow("")
    val query: MutableStateFlow<String> get() = _query


    fun getFavoriteUsers() {
        viewModelScope.launch {
            repository.getFavoriteUsers().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { users ->
                _uiState.value = UiState.Success(users)
            }
        }
    }

    @OptIn(FlowPreview::class)
    fun searchFavoriteUsers(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            _query.debounce(300).collectLatest {
                repository.searchFavoriteUser(
                    newQuery
                ).collect {
                    _uiState.value = UiState.Success(it)
                }
            }
        }
    }
    
}