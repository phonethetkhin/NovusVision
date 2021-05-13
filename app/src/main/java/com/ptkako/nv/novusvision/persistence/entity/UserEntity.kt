package com.ptkako.nv.novusvision.persistence.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "tbl_user", indices = [Index(value = ["user_id"], unique = true)]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "email_address") val emailAddress: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "recovery_question") val recoveryQuestion: String,
    @ColumnInfo(name = "recovery_answer") val recoveryAnswer: String,
    @ColumnInfo(name = "vip_plan") val vipPlan: Int
) : Parcelable
