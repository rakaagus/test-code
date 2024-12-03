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
    val id: Int = 0,

    var title: String,

    var description: String = "",

    var date: String,

    var isTrash: Boolean,

    var priority: PriorityTodo
): Parcelable
