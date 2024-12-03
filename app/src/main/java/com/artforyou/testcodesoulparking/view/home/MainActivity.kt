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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artforyou.testcodesoulparking.R
import com.artforyou.testcodesoulparking.databinding.ActivityMainBinding
import com.artforyou.testcodesoulparking.domain.model.Todos
import com.artforyou.testcodesoulparking.utils.AnimateFadeIn
import com.artforyou.testcodesoulparking.utils.SimpleSwipeTouch
import com.artforyou.testcodesoulparking.utils.TodoSwipeHelper
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


        viewModel.isLoading.observe(this){
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvListTodo.layoutManager = layoutManager

        val adapter = TodosAdapter()
        binding.rvListTodo.adapter = adapter
        binding.rvListTodo.itemAnimator = AnimateFadeIn()

        val swipeHelper = SimpleSwipeTouch(adapter, viewModel, this)
        val itemTouchHelper = ItemTouchHelper(swipeHelper)
        itemTouchHelper.attachToRecyclerView(binding.rvListTodo)

        viewModel.getAllTodos().observe(this){
            if (it.isEmpty()) {
                binding.rvListTodo.visibility = View.GONE
                binding.animationView.visibility = View.VISIBLE
            } else {
                binding.rvListTodo.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
                adapter.submitList(it)
            }
        }



        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddAndDetailActivity::class.java)
            intent.putExtra(AddAndDetailActivity.KEY_ID_ADD_OR_UPDATE, "Add")
            startActivity(intent)

        }
    }
}