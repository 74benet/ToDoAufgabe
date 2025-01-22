package com.example.todoa9.model

data class ToDo(
    val id: Int = 0,
    val name: String,
    val priority: Int,
    val endTime: Long,
    val description: String,
    val status: Boolean
)
