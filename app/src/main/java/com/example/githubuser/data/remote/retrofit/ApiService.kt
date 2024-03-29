package com.example.githubuser.data.remote.retrofit

import com.example.githubuser.BuildConfig
import com.example.githubuser.data.remote.response.DetailUserResponse
import com.example.githubuser.data.remote.response.SearchUserResponse
import com.example.githubuser.data.remote.response.UserListResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Call<List<UserListResponseItem>>

    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Query("per_page") perPage: Int
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UserListResponseItem>>
    
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UserListResponseItem>>
}