package com.artforyou.testcodesoulparking.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.artforyou.testcodesoulparking.data.local.entities.TodoEntities
import com.artforyou.testcodesoulparking.view.home.MainViewModel
import com.artforyou.testcodesoulparking.view.home.adapter.TodosAdapter

class AnimateFadeIn: DefaultItemAnimator(){
    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        val view = holder.itemView
        view.alpha = 0f
        val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        animator.duration = 500
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                dispatchAddFinished(holder)
            }
        })
        animator.start()
        return true
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        if (newHolder != null) {
            val view = newHolder.itemView
            view.alpha = 0f
            val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
            animator.duration = 500
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    dispatchChangeFinished(newHolder, false)
                }
            })
            animator.start()
        }
        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        val view = holder.itemView
        val animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        animator.duration = 500
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                dispatchRemoveFinished(holder)
                view.alpha = 1f
            }
        })
        animator.start()
        return true
    }

    override fun runPendingAnimations() {
        super.runPendingAnimations()
    }

    override fun endAnimations() {
        super.endAnimations()
    }
}

class TodoSwipeHelper(
    private val adapter: TodosAdapter,
    private val onItemSwiped: (TodoEntities) -> Unit,
    private val onItemMoved: (fromPosition: Int, toPosition: Int) -> Unit,
): ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition

        onItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val item = adapter.currentList[position]

        onItemSwiped(item)
    }

    override fun isLongPressDragEnabled(): Boolean = true
    override fun isItemViewSwipeEnabled(): Boolean = true
}

open class SimpleSwipeTouch(
    private val adapter: TodosAdapter,
    private val viewModel: MainViewModel,
    private val context: Context
): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private var swipedItem: TodoEntities? = null

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val item = adapter.currentList[position]
        swipedItem = adapter.currentList[position]

        val newList = adapter.currentList.toMutableList()
        newList.removeAt(position)
        adapter.submitList(newList)


        val builder = AlertDialog.Builder(context)
        builder.setTitle("Yakin Hapus Todo?")
        builder.setMessage("Data yang sudah dihapus tidak bisa dikembalikan lagi")
        builder.setPositiveButton("Oke"){ _, _ ->
            viewModel.deleteTodo(item)
        }
        builder.setNegativeButton("Batal"){_, _ ->
            swipedItem.let {
                val newList = adapter.currentList.toMutableList()
                newList.add(position, it)

                adapter.submitList(newList)
            }
            swipedItem = null
        }
        builder.setOnCancelListener{
            swipedItem.let {
                val newList = adapter.currentList.toMutableList()
                newList.add(position, it)

                adapter.submitList(newList)
            }
            swipedItem = null
        }
        builder.show()
    }
}

