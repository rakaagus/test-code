package com.artforyou.testcodesoulparking.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artforyou.testcodesoulparking.data.local.entities.TodoEntities
import com.artforyou.testcodesoulparking.domain.repo.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepository: AppRepository
): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        _isLoading.value = true
    }

    fun getAllTodos(): LiveData<List<TodoEntities>> {
        _isLoading.value = false // Set loading state
        return appRepository.getAllTodosActive()
    }

    fun deleteTodo(todo: TodoEntities){
        viewModelScope.launch {
            appRepository.deleteTodos(todo)
        }
    }
}