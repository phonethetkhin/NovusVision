package com.ptkako.nv.novusvision.persistence.dao

import androidx.room.*
import com.ptkako.nv.novusvision.persistence.entity.GenreEntity

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenre(genreEntity: GenreEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGenre(genreEntity: GenreEntity)

    @Delete
    suspend fun deleteGenre(genreEntity: GenreEntity)

    @Query("SELECT * FROM tbl_genre")
    suspend fun getAllGenres(): List<GenreEntity>

    @Query("DELETE FROM tbl_genre")
    suspend fun deleteAllGenres()
}