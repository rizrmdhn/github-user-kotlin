package com.example.githubuser.di

import com.example.githubuser.data.remote.UserRepository
import com.example.githubuser.data.remote.retrofit.ApiConfig

object Injection {
    fun provideUserRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()

        return UserRepository(apiService)
    }

}