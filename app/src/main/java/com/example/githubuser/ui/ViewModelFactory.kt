package com.example.githubuser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.remote.UserRepository
import com.example.githubuser.ui.screen.detail.DetailScreenViewModel
import com.example.githubuser.ui.screen.favorite.FavoriteScreenViewModel
import com.example.githubuser.ui.screen.follower.FollowerScreenViewModel
import com.example.githubuser.ui.screen.following.FollowingScreenViewModel
import com.example.githubuser.ui.screen.home.HomeViewModel

class ViewModelFactory(
    private val repository: UserRepository,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java)) {
            return DetailScreenViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(FollowerScreenViewModel::class.java)) {
            return FollowerScreenViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(FollowingScreenViewModel::class.java)) {
            return FollowingScreenViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(FavoriteScreenViewModel::class.java)) {
            return FavoriteScreenViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}