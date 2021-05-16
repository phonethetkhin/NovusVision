package com.ptkako.nv.novusvision.persistence.dao

import androidx.room.*
import com.ptkako.nv.novusvision.persistence.entity.MovieInfoEntity

@Dao
interface MovieInfoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieInfo(movieInfoEntity: MovieInfoEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovieInfo(movieInfoEntity: MovieInfoEntity)

    @Delete
    suspend fun deleteMovieInfo(movieInfoEntity: MovieInfoEntity)

    @Query("SELECT * FROM tbl_movie_info")
    suspend fun getAllMovieInfo(): List<MovieInfoEntity>


    @Query("DELETE FROM tbl_movie_info")
    suspend fun deleteAllMovieInfo()
}