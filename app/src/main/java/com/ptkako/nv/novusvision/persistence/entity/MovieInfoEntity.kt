package com.ptkako.nv.novusvision.persistence.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "tbl_movie_info", indices = [Index(value = ["movie_info_id","content_id"], unique = true)]
)
data class MovieInfoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_info_id")
    val movieInfoId: Int,
    @ColumnInfo(name = "content_id") val contentId: Int,
    @ColumnInfo(name = "movie_name") val movieName: String,
    @ColumnInfo(name = "genre_ids") val genreIds: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "popularity") val popularity: Int,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "casts") val casts: String,
    @ColumnInfo(name = "subtitle") val subtitle: String?,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "duration") val duration: String,
    @ColumnInfo(name = "trailer_video_path") val trailerVideoPath: String,
) : Parcelable
