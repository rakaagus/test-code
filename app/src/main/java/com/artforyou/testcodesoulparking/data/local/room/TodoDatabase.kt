package com.artforyou.testcodesoulparking.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artforyou.testcodesoulparking.data.local.dao.TodoDao
import com.artforyou.testcodesoulparking.data.local.entities.TodoEntities

@Database(
    entities = [
        TodoEntities::class
    ],
    version = 1,
    exportSchema = true
)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}