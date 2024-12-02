package com.artforyou.testcodesoulparking.view.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artforyou.testcodesoulparking.R
import com.artforyou.testcodesoulparking.databinding.ItemTodosListBinding
import com.artforyou.testcodesoulparking.domain.model.Todos
import com.artforyou.testcodesoulparking.utils.PriorityTodo
import com.artforyou.testcodesoulparking.view.add_detail.AddAndDetailActivity

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

        val context = holder.binding.root.context

        val priorityColor = when(dataTodos.priority){
            PriorityTodo.HIGH -> ContextCompat.getColor(context, R.color.purple)
            PriorityTodo.MEDIUM -> ContextCompat.getColor(context, R.color.green_tone)
            PriorityTodo.LOW -> ContextCompat.getColor(context, R.color.orange_tone)
        }

        holder.binding.itemRoot.setCardBackgroundColor(priorityColor)

        holder.binding.itemRoot.setOnClickListener {
            val intent = Intent(it.context, AddAndDetailActivity::class.java)
            intent.putExtra(AddAndDetailActivity.KEY_ID_ADD_OR_UPDATE, "Update")
            intent.putExtra(AddAndDetailActivity.KEY_EXTRA_PARCEL, dataTodos)
            it.context.startActivity(intent)
        }
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