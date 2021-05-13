package com.ptkako.nv.novusvision.persistence.dao

import androidx.room.*
import com.ptkako.nv.novusvision.persistence.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userEntity: UserEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(userEntity: UserEntity)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)

    @Query("SELECT * FROM tbl_user")
    suspend fun getAllUsers(): List<UserEntity>


    @Query("DELETE FROM tbl_user")
    suspend fun deleteAllUsers()
}