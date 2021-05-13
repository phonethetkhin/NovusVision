package com.ptkako.nv.novusvision.persistence.dao

import androidx.room.*
import com.ptkako.nv.novusvision.persistence.entity.UserEntity
import com.ptkako.nv.novusvision.persistence.entity.UserMovieEntity
import com.ptkako.nv.novusvision.persistence.entity.UserSeriesEntity

@Dao
interface UserSeriesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserSeries(userSeriesEntity: UserSeriesEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserSeries(userSeriesEntity: UserSeriesEntity)

    @Delete
    suspend fun deleteUserSeries(userSeriesEntity: UserSeriesEntity)

    @Query("SELECT * FROM tbl_user_series")
    suspend fun getAllUserSeries(): List<UserSeriesEntity>


    @Query("DELETE FROM tbl_user_series")
    suspend fun deleteAllUserSeries()
}