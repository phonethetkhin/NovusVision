package com.ptkako.nv.novusvision.persistence.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tbl_user_movie")
data class UserMovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "last_played_time") val lastPlayedTime: String,
    @ColumnInfo(name = "finish") val finish: Int
) : Parcelable
