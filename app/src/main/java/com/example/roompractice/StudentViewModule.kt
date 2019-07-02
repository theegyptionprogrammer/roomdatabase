package com.example.roompractice

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.provider.ContactsContract.CommonDataKinds.Note




class StudentViewModule(application: Application) : AndroidViewModel(application) {

    private val db : StudentDatabase? = StudentDatabase.getInsance(application)
    internal val allStudents : LiveData<MutableList<StudentEntites>> = db!!.studentDao().getAll()

    fun insert(student: StudentEntites){
        db!!.studentDao().insert(student)
    }


    fun deleteAllStudents() {
        db!!.studentDao().deleteAll()
    }


    fun getCount() : LiveData<Int>{
        return db!!.studentDao().getCount()
    }
}

