package com.example.roompractice

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.student.view.*

class RecyclerViewAdapter(var listStudents: ArrayList<StudentEntites>, val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.viewHolder>() {

    val images : IntArray? = null

    fun addStudent(listStudents : ArrayList<StudentEntites>){
        this.listStudents = listStudents
        notifyDataSetChanged()
    }

    fun removeStudent(position: Int ){
        listStudents.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clearAll(listStudents: ArrayList<StudentEntites>){
        this.listStudents.removeAll(listStudents)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): viewHolder {
        return viewHolder(LayoutInflater.from(context).inflate(R.layout.student , parent , false))
    }

    override fun getItemCount(): Int = listStudents.size


    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.onBind(listStudents[position])
    }

    class viewHolder(val view : View) : RecyclerView.ViewHolder(view){

        fun onBind(studentEntites: StudentEntites){
            view.nameTV.text = studentEntites.studemtName
            view.addressTV.text = studentEntites.studentAddress
            view.courseTV.text = studentEntites.studentCourse
            view.idTV.text = studentEntites.studentId.toString()
          //  view.profilephoto2.setImageBitmap(studentEntites.profilePhoto)
        }

    }




}