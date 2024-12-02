package com.artforyou.testcodesoulparking.domain.model

import android.os.Parcelable
import com.artforyou.testcodesoulparking.utils.PriorityTodo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todos(
    val id: Int,
    val title: String,
    val description: String = "",
    val date: String,
    val isTrash: Boolean,
    val priority: PriorityTodo
) : Parcelable
