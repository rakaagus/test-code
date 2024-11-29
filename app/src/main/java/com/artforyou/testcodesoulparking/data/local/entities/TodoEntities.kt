package com.artforyou.testcodesoulparking.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos_table")
data class TodoEntities(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val title: String,

    val description: String = "",

    val date: String,

    val isTrash: Boolean
)
