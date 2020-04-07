package com.falanapp.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "task")
data class TaskEntry(
    @PrimaryKey(autoGenerate = true)
    private val id: Int,
    private val description: String,
    private val priority: Int,
    @ColumnInfo(name = "updated_at")
    private val updatedAt: Date
) {
    fun getId() = id

    fun getDescription() = description

    fun getPriority() = priority

    fun getUpdatedAt() = updatedAt


}




