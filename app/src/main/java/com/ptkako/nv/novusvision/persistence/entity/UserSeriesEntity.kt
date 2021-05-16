package com.ptkako.nv.novusvision.persistence.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tbl_user_series")
data class UserSeriesEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "series_id") val seriesId: Int,
    @ColumnInfo(name = "last_played_time") val lastPlayedTime: String,
    @ColumnInfo(name = "finish") val finish: Int
) : Parcelable
