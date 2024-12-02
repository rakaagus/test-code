package com.artforyou.testcodesoulparking.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.artforyou.testcodesoulparking.data.local.entities.TodoEntities
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos_table WHERE isTrash = 0 ORDER BY id DESC")
    fun getAllTodos(): LiveData<List<TodoEntities>>

    @Query("SELECT * FROM todos_table WHERE isTrash = 1")
    fun getAllTrashTodos(): LiveData<List<TodoEntities>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todo: TodoEntities)

    @Delete
    suspend fun deleteTodo(todo: TodoEntities)

    @Update
    suspend fun updateTodo(todo: TodoEntities)
}