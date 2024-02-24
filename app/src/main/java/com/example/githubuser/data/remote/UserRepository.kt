package com.example.githubuser.data.remote

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.githubuser.data.remote.response.DetailUserResponse
import com.example.githubuser.data.remote.response.SearchUserResponse
import com.example.githubuser.data.remote.response.UserListResponseItem
import com.example.githubuser.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val apiService: ApiService
) {
    private val data = mutableStateListOf<UserListResponseItem>()
    private var detailUser by mutableStateOf<DetailUserResponse?>(null)
    private var detailUserFollower = mutableStateListOf<UserListResponseItem>()
    private var detailUserFollowing = mutableStateListOf<UserListResponseItem>()

    private var since by mutableIntStateOf(0)
    private val perPage by mutableIntStateOf(10)

    fun getUsers(): Flow<List<UserListResponseItem>> {
        val client = apiService.getUsers(
            since = since,
            perPage = perPage
        )
        client.enqueue(object : Callback<List<UserListResponseItem>> {
            override fun onResponse(
                call: Call<List<UserListResponseItem>>,
                response: Response<List<UserListResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // check if the id is already in the list
                        val filteredList = responseBody.filter { user ->
                            data.none { it.id == user.id }
                        }
                        data.addAll(filteredList)
                    }
                } else {
                    throw Exception(response.message())
                }
            }

            override fun onFailure(call: Call<List<UserListResponseItem>>, t: Throwable) {
                throw Exception(t.message.toString())
            }
        })

        return flowOf(data)
    }

    fun getMoreUsers(): Flow<List<UserListResponseItem>> {
        since += perPage
        return getUsers()
    }

    fun searchUser(
        query: String
    ): Flow<List<UserListResponseItem>> {
        val client = apiService.searchUsers(
            query = query,
            perPage = perPage
        )
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        data.clear()
                        data.addAll(responseBody.items)
                    }
                } else {
                    throw Exception(response.message())
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                throw Exception(t.message.toString())
            }
        })


        return flowOf(data)
    }

    fun getUserDetails(
        username: String
    ): Flow<DetailUserResponse?> {
        val client = apiService.getUserDetail(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        detailUser = responseBody
                    } else {
                        throw Exception("User not found")
                    }
                } else {
                    throw Exception(response.message())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                throw Exception(t.message.toString())
            }
        })


        return flowOf(detailUser)
    }

    fun getUserDetailsFollower(
        username: String
    ): Flow<List<UserListResponseItem>> {
        val client = apiService.getFollowers(username)
        client.enqueue(object : Callback<List<UserListResponseItem>> {
            override fun onResponse(
                call: Call<List<UserListResponseItem>>,
                response: Response<List<UserListResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        detailUserFollower.addAll(responseBody)
                    }
                } else {
                    throw Exception(response.message())
                }
            }

            override fun onFailure(call: Call<List<UserListResponseItem>>, t: Throwable) {
                throw Exception(t.message.toString())
            }
        })

        return flowOf(detailUserFollower)
    }

    fun getUserDetailsFollowing(
        username: String
    ): Flow<List<UserListResponseItem>> {
        val client = apiService.getFollowing(username)
        client.enqueue(object : Callback<List<UserListResponseItem>> {
            override fun onResponse(
                call: Call<List<UserListResponseItem>>,
                response: Response<List<UserListResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        detailUserFollowing.addAll(responseBody)
                    }
                } else {
                    throw Exception(response.message())
                }
            }

            override fun onFailure(call: Call<List<UserListResponseItem>>, t: Throwable) {
                throw Exception(t.message.toString())
            }
        })

        return flowOf(detailUserFollowing)
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}