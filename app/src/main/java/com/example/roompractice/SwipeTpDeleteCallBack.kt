package com.example.roompractice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

abstract class SwipeTpDeleteCallBack(context: Context) : ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.LEFT) {


    private val deleteIcon = ContextCompat.getDrawable(context , R.drawable.ic_delete_black_24dp)
    private val intrinsicHeight = deleteIcon!!.intrinsicHeight
    private val intrinsicWidth = deleteIcon!!.minimumWidth
    private val background = ColorDrawable()
    private val backgroundcolour = Color.RED

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        if (viewHolder.adapterPosition == 10) return 0
        return super.getMovementFlags(recyclerView, viewHolder)
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        background.color = backgroundcolour
        background.setBounds(itemView.left + dX.toInt() , itemView.bottom , itemView.right , itemView.top)
        background.draw(c)

        val deleteIconTop = itemView.top +( itemHeight - intrinsicHeight ) / 2
        val deleteIconBottom = deleteIconTop + intrinsicHeight
        val deleteIconMargain = ( itemHeight - intrinsicHeight ) / 2
        val deleteIconLeft = itemView.right - deleteIconMargain - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargain

        deleteIcon!!.setBounds(deleteIconLeft , deleteIconTop ,deleteIconRight ,deleteIconBottom)
        deleteIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)


    }
}