package com.example.roompractice

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_student.*
import org.jetbrains.anko.doAsync

class AddStudentActivity : AppCompatActivity() {

    private lateinit var module : StudentViewModule
    var studentDatabase : StudentDatabase ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        studentDatabase = StudentDatabase.getInsance(this)
        module = ViewModelProviders.of(this).get(StudentViewModule::class.java)

        saveBTN.setOnClickListener {
            doAsync {
                val name = findViewById<EditText>(R.id.nameET).text.toString()
                val address = findViewById<EditText>(R.id.addressET).text.toString()
                val course = findViewById<EditText>(R.id.courseET).text.toString()
                val id =  Integer.parseInt(findViewById<EditText>(R.id.idET).text.toString())
                module.insert(StudentEntites(id , name , course ,address))
            }
            Toast.makeText(this , "new student have been added" , Toast.LENGTH_SHORT).show()
            nameET.setText("")
            addressET.setText("")
            courseET.setText("")
            idET.setText("")

            val view = this.currentFocus
            if (view != null){
                val inn = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inn.hideSoftInputFromWindow(view.windowToken , 0)
            }


        }
    }
}