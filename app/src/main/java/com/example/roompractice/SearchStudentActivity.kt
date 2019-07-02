package com.example.roompractice

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.widget.LinearLayout
import android.widget.SearchView


class SearchStudentActivity : AppCompatActivity(){

    private lateinit var adapter: RecyclerViewAdapter
    private  var searchView: SearchView ?= null
    private lateinit var module: StudentViewModule
    private  var studentDatabase : StudentDatabase ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_student)
        studentDatabase = StudentDatabase.getInsance(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        adapter = RecyclerViewAdapter(arrayListOf() , this)
        recyclerView.layoutManager = LinearLayoutManager(this , LinearLayout.VERTICAL , false)
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        menuInflater.inflate(R.menu.search_menu , menu)
        searchView = menu.findItem(R.id.search_bar).actionView as SearchView
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView!!.isSubmitButtonEnabled
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                loadStudents(newText!!)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                loadStudents(query!!)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    fun loadStudents(student : String){
        val searchList = "%$student%"
        studentDatabase!!.studentDao().searchByName(searchList).observe(this , Observer { students ->
            adapter.addStudent(students as ArrayList<StudentEntites>)
        }
        )
    }
}