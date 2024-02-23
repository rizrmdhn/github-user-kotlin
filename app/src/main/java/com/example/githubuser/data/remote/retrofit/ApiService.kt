package com.example.githubuser.data.remote.retrofit

import com.example.githubuser.data.remote.response.UserListResponse
import com.example.githubuser.data.remote.response.UserListResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Call<List<UserListResponseItem>>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): UserListResponse

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): UserListResponse
}