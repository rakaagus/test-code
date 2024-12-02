package com.artforyou.testcodesoulparking.view.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.artforyou.testcodesoulparking.R
import com.artforyou.testcodesoulparking.databinding.ActivityMainBinding
import com.artforyou.testcodesoulparking.domain.model.Todos
import com.artforyou.testcodesoulparking.view.add_detail.AddAndDetailActivity
import com.artforyou.testcodesoulparking.view.home.adapter.TodosAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.animationView.visibility = View.VISIBLE

        viewModel.isLoading.observe(this){
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvListTodo.layoutManager = layoutManager

        viewModel.getAllTodos().observe(this){

            if (it.isEmpty()) {
                binding.rvListTodo.visibility = View.GONE  // Sembunyikan RecyclerView
                binding.animationView.visibility = View.VISIBLE // Tampilkan animasi
            } else {
                binding.rvListTodo.visibility = View.VISIBLE // Tampilkan RecyclerView
                binding.animationView.visibility = View.GONE // Sembunyikan animasi
            }

            val adapter = TodosAdapter()
            val data = it.map { todo->
                Todos(
                    id = todo.id,
                    title = todo.title,
                    description = todo.description,
                    date = todo.date,
                    isTrash = todo.isTrash,
                    priority = todo.priority
                )
            }
            adapter.submitList(data)
            binding.rvListTodo.adapter = adapter
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddAndDetailActivity::class.java)
            intent.putExtra(AddAndDetailActivity.KEY_ID_ADD_OR_UPDATE, "Add")
            startActivity(intent)

        }
    }
}