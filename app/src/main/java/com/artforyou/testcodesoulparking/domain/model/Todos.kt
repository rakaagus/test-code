package com.artforyou.testcodesoulparking.domain.model

data class Todos(
    val id: Int,
    val title: String,
    val description: String = "",
    val date: String,
    val isTrash: Boolean
)
