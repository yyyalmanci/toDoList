package com.falanapp.todolist.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Database(
    entities = [TaskEntry::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class TaskDb : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}

@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY priority")
    fun loadAllTasks(): Single<List<TaskEntry>>

    @Query("SELECT * FROM task WHERE task.id =:id")
    fun loadTask(id: Int): Single<TaskEntry>

    @Insert
    fun insertTask(taskEntry: TaskEntry): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTask(taskEntry: TaskEntry): Completable

    @Delete
    fun deleteTask(taskEntry: TaskEntry): Completable
}