package com.artforyou.testcodesoulparking.domain.repo

import androidx.lifecycle.LiveData
import com.artforyou.testcodesoulparking.data.local.entities.TodoEntities

interface AppRepository {
    fun getAllTodosActive(): LiveData<List<TodoEntities>>

    suspend fun addTodos(todo: TodoEntities)

    suspend fun deleteTodos(todo: TodoEntities)

    suspend fun updateTodos(todo: TodoEntities)
}