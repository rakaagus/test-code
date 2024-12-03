package com.artforyou.testcodesoulparking.domain.model

import android.os.Parcelable
import com.artforyou.testcodesoulparking.utils.PriorityTodo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todos(
    val id: Int = 0,
    var title: String,
    var description: String = "",
    var date: String,
    val isTrash: Boolean,
    var priority: PriorityTodo
) : Parcelable
