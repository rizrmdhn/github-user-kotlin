package com.example.githubuser.di

import android.content.Context
import com.example.githubuser.data.local.room.FavoriteUserDatabase
import com.example.githubuser.data.remote.UserRepository
import com.example.githubuser.data.remote.retrofit.ApiConfig

object Injection {
    fun provideUserRepository(
        context: Context
    ): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserDatabase.getInstance(context)
        val dao = database.favoriteUserDao()

        return UserRepository(apiService, dao)
    }

}