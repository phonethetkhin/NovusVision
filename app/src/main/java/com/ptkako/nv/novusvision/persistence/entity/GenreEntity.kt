package com.ptkako.nv.novusvision.persistence.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "tbl_genre", indices = [Index(value = ["genre_id"], unique = true)]
)
data class GenreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "genre_id")
    val genreId: Int,
    @ColumnInfo(name = "genre_name") val genreName: String
) : Parcelable
