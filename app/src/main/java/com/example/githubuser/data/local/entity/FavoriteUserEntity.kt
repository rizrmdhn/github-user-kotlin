package com.example.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "favorite_user")
class FavoriteUserEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "login")
    val login: String,

    @field:ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    ) : Parcelable

