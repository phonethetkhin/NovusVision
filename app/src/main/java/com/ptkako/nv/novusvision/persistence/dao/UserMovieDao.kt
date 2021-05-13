package com.ptkako.nv.novusvision.persistence.dao

import androidx.room.*
import com.ptkako.nv.novusvision.persistence.entity.UserMovieEntity

@Dao
interface UserMovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserMovie(userMovieEntity: UserMovieEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserMovie(userMovieEntity: UserMovieEntity)

    @Delete
    suspend fun deleteUserMovie(userMovieEntity: UserMovieEntity)

    @Query("SELECT * FROM tbl_user_movie")
    suspend fun getAllUserMovies(): List<UserMovieEntity>


    @Query("DELETE FROM tbl_user_movie")
    suspend fun deleteAllUserMovies()
}