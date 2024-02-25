package com.example.githubuser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.local.dataStore.SettingPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GithubUserAppViewModel(
    private val preferences: SettingPreferences
) : ViewModel() {
    private val _darkMode: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val darkMode: MutableStateFlow<Boolean> get() = _darkMode

    fun getDarkMode() {
        viewModelScope.launch {
            preferences.getThemeSetting().collect {
                _darkMode.value = it
            }
        }
    }

    fun setDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkMode)
            _darkMode.value = isDarkMode
        }
    }
}