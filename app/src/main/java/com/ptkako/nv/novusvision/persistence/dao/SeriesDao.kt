package com.ptkako.nv.novusvision.persistence.dao

import androidx.room.*
import com.ptkako.nv.novusvision.persistence.entity.MovieEntity
import com.ptkako.nv.novusvision.persistence.entity.SeriesEntity
import com.ptkako.nv.novusvision.persistence.entity.UserEntity

@Dao
interface SeriesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSeries(seriesEntity: SeriesEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSeries(seriesEntity: SeriesEntity)

    @Delete
    suspend fun deleteSeries(seriesEntity: SeriesEntity)

    @Query("SELECT * FROM tbl_series")
    suspend fun getAllSeries(): List<SeriesEntity>


    @Query("DELETE FROM tbl_series")
    suspend fun deleteAllSeries()
}