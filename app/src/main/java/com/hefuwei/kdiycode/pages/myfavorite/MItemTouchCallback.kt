package com.hefuwei.kdiycode.pages.myfavorite

import android.graphics.Canvas
import android.graphics.Color
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * 拦截左右滑动事件，移除Item
 */
class MItemTouchCallback(private val listener: OnItemChangeListener): ItemTouchHelper.Callback() {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean = true

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = 0
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onItemRemove(viewHolder.adapterPosition)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val alpha = 1 - Math.abs(dX / viewHolder.itemView.width)
            val itemView = viewHolder.itemView
            itemView.alpha = alpha
            itemView.scaleX = alpha
            itemView.scaleY = alpha
            if (dX < -itemView.width / 2) {
                itemView.translationX = (-itemView.width / 2).toFloat()
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val itemView = viewHolder.itemView
        itemView.setBackgroundColor(Color.WHITE)
        itemView.alpha = 1f
        itemView.scaleX = 1f
        itemView.scaleY = 1f
    }

    interface OnItemChangeListener {
        fun onItemRemove(position: Int)
    }
}

