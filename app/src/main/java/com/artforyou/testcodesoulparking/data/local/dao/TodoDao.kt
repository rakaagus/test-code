package com.artforyou.testcodesoulparking.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artforyou.testcodesoulparking.data.local.entities.TodoEntities
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos_table WHERE isTrash = 0 ORDER BY id DESC")
    fun getAllTodos(): Flow<List<TodoEntities>>

    @Query("SELECT * FROM todos_table WHERE isTrash = 1")
    fun getAllTrashTodos(): Flow<List<TodoEntities>>

    @Query("SELECT * FROM todos_table WHERE id = :id")
    suspend fun getTodoById(id: Int): TodoEntities

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todo: TodoEntities)

    @Delete
    suspend fun deleteTodo(todo: TodoEntities)
}