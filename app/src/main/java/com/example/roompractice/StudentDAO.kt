package com.example.roompractice

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Delete


@Dao
interface StudentDAO {

    @Query("SELECT * FROM Studententites")
    fun getAll() : LiveData<MutableList<StudentEntites>>

    @Query("SELECT  * FROM StudentEntites WHERE studentName LIKE :searchName")
    fun searchByName(searchName : String) : LiveData<MutableList<StudentEntites>>

    @Query("DELETE FROM StudentEntites")
    fun deleteAll() : LiveData<StudentEntites>

    @Query("SELECT COUNT (studentId) FROM StudentEntites")
    fun getCount() : LiveData<Int>

    @Insert
    fun insert(vararg student: StudentEntites)
}