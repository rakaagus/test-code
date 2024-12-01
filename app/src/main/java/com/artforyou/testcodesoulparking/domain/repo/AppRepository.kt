package com.artforyou.testcodesoulparking.domain.repo

import com.artforyou.testcodesoulparking.data.local.entities.TodoEntities
import kotlinx.coroutines.flow.Flow


typealias AllActiveTodos = List<TodoEntities>

interface AppRepository {
    fun getAllTodosActive(): Flow<AllActiveTodos>

    suspend fun addTodos(todo: TodoEntities)

    suspend fun deleteTodos(todo: TodoEntities)

    suspend fun getTodoById(id: Int): TodoEntities
}