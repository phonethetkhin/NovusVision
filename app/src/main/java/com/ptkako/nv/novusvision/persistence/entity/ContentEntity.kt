package com.ptkako.nv.novusvision.persistence.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "tbl_content", indices = [Index(value = ["content_id"], unique = true)]
)
data class ContentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "content_id")
    val contentId: Int,
    @ColumnInfo(name = "image_path") val imagePath: String,
    @ColumnInfo(name = "image_cover_path") val imageCoverPath: String
) : Parcelable
