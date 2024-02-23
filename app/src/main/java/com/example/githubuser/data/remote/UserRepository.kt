package com.example.githubuser.data.remote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.githubuser.data.remote.response.UserListResponseItem
import com.example.githubuser.data.remote.retrofit.ApiService
import com.example.githubuser.ui.screen.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val apiService: ApiService
) {
    private val data = mutableStateListOf<UserListResponseItem>()
    private var error by mutableStateOf("")
    private var since by mutableIntStateOf(0)
    private val perPage by mutableIntStateOf(10)

    fun getUsers(): Flow<UiState<List<UserListResponseItem>>> {
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
                    error = response.message()
                }
            }

            override fun onFailure(call: Call<List<UserListResponseItem>>, t: Throwable) {
                error = t.message.toString()
            }
        })

        if (error.isEmpty() && data.isEmpty()) {
            return flowOf(UiState.Loading)
        }

        if (error.isNotEmpty()) {
            return flowOf(UiState.Error(error))
        }

        return flowOf(UiState.Success(data))
    }

    fun getMoreUsers(): Flow<UiState<List<UserListResponseItem>>> {
        since += perPage
        return getUsers()
    }

//    fun getFollowers(username: String): LiveData<Result<UserListResponse>> = liveData {
//        emit(Result.Loading)
//        try {
//            val followers = apiService.getFollowers(username)
//            emit(Result.Success(followers))
//        } catch (e: Exception) {
//            Log.d("UserRepository", "getFollowers: ${e.message.toString()} ")
//            emit(Result.Error(e.message.toString()))
//        }
//    }
//
//    fun getFollowersCount(username: String): LiveData<Result<Int>> = liveData {
//        emit(Result.Loading)
//        try {
//            val followers = apiService.getFollowers(username)
//            emit(Result.Success(followers.userListResponse.size))
//        } catch (e: Exception) {
//            Log.d("UserRepository", "getFollowers: ${e.message.toString()} ")
//            emit(Result.Error(e.message.toString()))
//        }
//    }
//
//    fun getFollowing(username: String): LiveData<Result<UserListResponse>> = liveData {
//        emit(Result.Loading)
//        try {
//            val following = apiService.getFollowing(username)
//            emit(Result.Success(following))
//        } catch (e: Exception) {
//            Log.d("UserRepository", "getFollowing: ${e.message.toString()} ")
//            emit(Result.Error(e.message.toString()))
//        }
//    }

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