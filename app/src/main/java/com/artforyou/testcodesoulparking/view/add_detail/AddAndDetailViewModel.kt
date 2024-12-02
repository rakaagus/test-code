package com.artforyou.testcodesoulparking.view.add_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artforyou.testcodesoulparking.data.local.entities.TodoEntities
import com.artforyou.testcodesoulparking.domain.repo.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAndDetailViewModel @Inject constructor(
    private val repository: AppRepository
): ViewModel(){

    fun addTodo(todo: TodoEntities){
        viewModelScope.launch {
            repository.addTodos(todo)
        }
    }

    fun updateTodo(todo: TodoEntities){
        viewModelScope.launch {
            repository.updateTodos(todo)
        }
    }

}