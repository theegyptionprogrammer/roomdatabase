package com.example.roompractice

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.Bitmap

@Entity (tableName = "StudentEntites")
data class StudentEntites(
    @PrimaryKey(autoGenerate = true) val studentId: Int?,
    @ColumnInfo(name = "studentName") val studemtName: String?,
    @ColumnInfo(name = "studentCourse") val studentCourse: String?,
    @ColumnInfo(name = "studentAddress") val studentAddress: String?,
    @ColumnInfo(name = "profilePhoto") val profilePhoto : Bitmap?
)