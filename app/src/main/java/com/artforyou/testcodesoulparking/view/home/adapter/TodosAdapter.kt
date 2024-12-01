package com.artforyou.testcodesoulparking.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artforyou.testcodesoulparking.databinding.ItemTodosListBinding
import com.artforyou.testcodesoulparking.domain.model.Todos

class TodosAdapter: ListAdapter<Todos, TodosAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(val binding: ItemTodosListBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosAdapter.ViewHolder {
        val binding = ItemTodosListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataTodos = getItem(position)
        holder.binding.tvTitleTodo.text = dataTodos.title
        holder.binding.tvPriority.text = dataTodos.priority.name
        holder.binding.tvDateTodo.text = dataTodos.date
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Todos>(){
            override fun areItemsTheSame(oldItem: Todos, newItem: Todos): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Todos, newItem: Todos): Boolean {
                return oldItem == newItem
            }
        }
    }
}