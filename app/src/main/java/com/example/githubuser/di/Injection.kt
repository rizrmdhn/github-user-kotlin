package com.example.githubuser.di

import com.example.githubuser.data.remote.UserRepository
import com.example.githubuser.data.remote.retrofit.ApiConfig
import com.example.githubuser.data.remote.retrofit.ApiService

object Injection {
    fun provideRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()

        return UserRepository.getInstance(
            apiService
        )
    }
}