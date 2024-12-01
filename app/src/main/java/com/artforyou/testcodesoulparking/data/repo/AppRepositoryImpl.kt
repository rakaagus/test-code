package com.artforyou.testcodesoulparking.data.repo

import com.artforyou.testcodesoulparking.data.local.dao.TodoDao
import com.artforyou.testcodesoulparking.data.local.entities.TodoEntities
import com.artforyou.testcodesoulparking.domain.repo.AllActiveTodos
import com.artforyou.testcodesoulparking.domain.repo.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val todosDao: TodoDao
) : AppRepository{
    override fun getAllTodosActive(): Flow<AllActiveTodos> {
        return todosDao.getAllTodos()
    }

    override suspend fun addTodos(todo: TodoEntities) {
        todosDao.insertTodo(todo)
    }

    override suspend fun deleteTodos(todo: TodoEntities) {
        todosDao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int): TodoEntities {
        return todosDao.getTodoById(id)
    }
}