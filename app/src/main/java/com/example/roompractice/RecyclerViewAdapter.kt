package com.example.roompractice

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_add_student.view.*
import kotlinx.android.synthetic.main.student.view.*

class RecyclerViewAdapter(var listStudents: ArrayList<StudentEntites>, val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.viewHolder>() {


    fun addStudent(listStudents : ArrayList<StudentEntites>){
        this.listStudents = listStudents
        notifyDataSetChanged()
    }

    fun removeStudent(position: Int ){
        listStudents.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clearAll(listStudents: StudentEntites?){
        this.listStudents.removeAll(listStudents)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): viewHolder {
        return viewHolder(LayoutInflater.from(context).inflate(R.layout.student , parent , false))
    }

    override fun getItemCount(): Int = listStudents.size


    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.nameTV.text = listStudents[position].studemtName
        holder.addressTV.text = listStudents[position].studentAddress
        holder.courseTV.text = listStudents[position].studentCourse
        holder.idTV.text = listStudents[position].studentId.toString()
    }

    class viewHolder(view : View) : RecyclerView.ViewHolder(view){
        val nameTV = view.nameTV
        val addressTV = view.addressTV
        val courseTV = view.courseTV
        val idTV = view.idTV
        val photoFromGallery = view.photoFromGallery
        val photoFromCamera  = view.photoFromCamera
    }




}