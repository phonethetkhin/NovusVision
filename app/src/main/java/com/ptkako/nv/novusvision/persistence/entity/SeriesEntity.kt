package com.ptkako.nv.novusvision.persistence.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "tbl_series", indices = [Index(value = ["series_id"], unique = true)]
)
data class SeriesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "series_id")
    val seriesId: Int,
    @ColumnInfo(name = "movie_info_id") val movieInfoId: Int,
    @ColumnInfo(name = "season_number") val seasonNumber: Int,
    @ColumnInfo(name = "episode_number") val episodeNumber: Int,
    @ColumnInfo(name = "video_path") val videoPath: String
) : Parcelable
