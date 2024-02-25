package com.example.githubuser.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubuser.data.local.entity.FavoriteUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUsers(): Flow<List<FavoriteUserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteUser(favoriteUser: FavoriteUserEntity)

    @Query("SELECT * FROM favorite_user WHERE login LIKE :query")
    fun searchFavoriteUser(query: String): Flow<List<FavoriteUserEntity>>

    @Query("DELETE FROM favorite_user WHERE login = :login")
    suspend fun deleteFavoriteUser(login: String)

    @Query("SELECT EXISTS(SELECT * FROM favorite_user WHERE login = :username)")
    fun isFavoriteUser(username: String): Flow<Boolean>
}