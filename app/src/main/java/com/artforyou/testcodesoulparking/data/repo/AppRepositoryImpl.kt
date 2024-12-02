package com.artforyou.testcodesoulparking.data.repo

import androidx.lifecycle.LiveData
import com.artforyou.testcodesoulparking.data.local.dao.TodoDao
import com.artforyou.testcodesoulparking.data.local.entities.TodoEntities
import com.artforyou.testcodesoulparking.domain.repo.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val todosDao: TodoDao
) : AppRepository{
    override fun getAllTodosActive(): LiveData<List<TodoEntities>> {
        return todosDao.getAllTodos()
    }

    override suspend fun addTodos(todo: TodoEntities) {
        todosDao.insertTodo(todo)
    }

    override suspend fun deleteTodos(todo: TodoEntities) {
        todosDao.deleteTodo(todo)
    }

    override suspend fun updateTodos(todo: TodoEntities) {
        todosDao.updateTodo(todo)
    }
}