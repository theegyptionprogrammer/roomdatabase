package com.example.roompractice

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

private lateinit var model : StudentViewModule
private var studentDatabase : StudentDatabase ?= null
private var recyclerViewAdapter : RecyclerViewAdapter ?= null
private var student : StudentEntites ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutManager = LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false)
        recyclerViewAdapter = RecyclerViewAdapter(arrayListOf() , this)
        val RecyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        RecyclerView.layoutManager = linearLayoutManager
        RecyclerView.adapter = recyclerViewAdapter
        RecyclerView.hasFixedSize()

        model = ViewModelProviders.of(this).get(StudentViewModule::class.java)
        model.allStudents.observe(this ,
            Observer { students->recyclerViewAdapter!!.addStudent((students as ArrayList<StudentEntites>))})


        studentDatabase = StudentDatabase.getInsance(this)
        insertBTN.setOnClickListener {
            val intent = Intent(this , AddStudentActivity::class.java)
            startActivity(intent)
        }

        searchBTN.setOnClickListener {
            val intent = Intent(this , SearchStudentActivity::class.java)
            startActivity(intent)
        }

        getCount.setOnClickListener {
            model.getCount().observe(this,
                Observer<Int> { integer -> studentsCount.setText(integer.toString()) })
        }

        val swipeHandler = object : SwipeTpDeleteCallBack(this){
            override fun onSwiped(viewholder: RecyclerView.ViewHolder, position: Int) {
                recyclerViewAdapter = recyclerview.adapter as RecyclerViewAdapter
                recyclerViewAdapter!!.removeStudent(viewholder.adapterPosition)

            }
         }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerview)

    }
}
