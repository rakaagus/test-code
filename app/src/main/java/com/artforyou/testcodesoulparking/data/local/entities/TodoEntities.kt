package com.artforyou.testcodesoulparking.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artforyou.testcodesoulparking.utils.PriorityTodo
import kotlinx.parcelize.Parcelize

@Entity(tableName = "todos_table")
@Parcelize
data class TodoEntities(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val title: String,

    val description: String = "",

    val date: String,

    val isTrash: Boolean,

    val priority: PriorityTodo
): Parcelable
