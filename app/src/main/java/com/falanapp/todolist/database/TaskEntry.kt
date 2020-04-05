package com.falanapp.todolist.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "task", primaryKeys = ["id"])
data class TaskEntry(
    @PrimaryKey(autoGenerate = true)
    private val id: Int,
    private val description: String,
    private val priority: Int,
    private val date: Date
) {
    fun getId() = id

    fun getDescriptipn() = description

    fun getPriority() = priority

    fun getDate() = date


}




