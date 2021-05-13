package com.ptkako.nv.novusvision.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ptkako.nv.novusvision.persistence.dao.*
import com.ptkako.nv.novusvision.persistence.entity.*

@Database(
    entities = [ContentEntity::class, GenreEntity::class, MovieEntity::class, MovieInfoEntity::class, SeriesEntity::class, UserEntity::class, UserMovieEntity::class, UserSeriesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun ContentDao(): ContentDao
    abstract fun GenreDao(): GenreDao
    abstract fun MovieDao(): MovieDao
    abstract fun MovieInfoDao(): MovieInfoDao
    abstract fun SeriesDao(): SeriesDao
    abstract fun UserDao(): UserDao
    abstract fun UserMovieDao(): UserMovieDao
    abstract fun UserSeriesDao(): UserSeriesDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDB(context).also { instance = it }
        }

        private fun buildDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java, "novus_vision.db"
            )
                .allowMainThreadQueries()
                .build()
    }

}