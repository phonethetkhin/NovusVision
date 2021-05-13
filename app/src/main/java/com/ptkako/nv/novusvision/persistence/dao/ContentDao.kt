package com.ptkako.nv.novusvision.persistence.dao

import androidx.room.*
import com.ptkako.nv.novusvision.persistence.entity.ContentEntity

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContent(contentEntity: ContentEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateContent(contentEntity: ContentEntity)

    @Delete
    suspend fun deleteContent(contentEntity: ContentEntity)

    @Query("SELECT * FROM tbl_content")
    suspend fun getAllContent(): List<ContentEntity>


    @Query("DELETE FROM tbl_content")
    suspend fun deleteAllContent()
}