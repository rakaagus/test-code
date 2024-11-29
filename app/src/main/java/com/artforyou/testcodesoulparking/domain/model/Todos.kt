package com.artforyou.testcodesoulparking.domain.model

import com.artforyou.testcodesoulparking.utils.PriorityTodo

data class Todos(
    val id: Int,
    val title: String,
    val description: String = "",
    val date: String,
    val isTrash: Boolean,
    val priority: PriorityTodo
)
