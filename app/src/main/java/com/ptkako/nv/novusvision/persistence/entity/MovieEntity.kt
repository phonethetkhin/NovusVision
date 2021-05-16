package com.ptkako.nv.novusvision.persistence.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "tbl_movie", indices = [Index(value = ["movie_id"], unique = true)]
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "movie_info_id") val movieInfoId: Int,
    @ColumnInfo(name = "video_path") val videoPath: String
) : Parcelable
