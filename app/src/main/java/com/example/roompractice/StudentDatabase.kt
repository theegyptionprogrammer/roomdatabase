package com.example.roompractice

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(StudentEntites::class) , version = 1)
abstract class StudentDatabase : RoomDatabase(){

    abstract fun studentDao() : StudentDAO

    companion object{
        private var Insance : StudentDatabase ?= null

        fun getInsance(context: Context) : StudentDatabase?{
            if (Insance == null){
                synchronized(StudentDatabase::class){
                    Insance = Room.databaseBuilder(context.getApplicationContext() , StudentDatabase::class.java ,"student.db")
                        .build()
                }
            }
            return Insance
        }

        fun destroyInstance() {
            Insance = null
        }
    }
}