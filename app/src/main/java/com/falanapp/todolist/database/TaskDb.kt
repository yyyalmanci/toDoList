package com.falanapp.todolist.database

import android.content.Context
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

    companion object {
        @Volatile
        private var INSTANCE: TaskDb? = null

        fun getInstance(context: Context): TaskDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDb::class.java,
                        "task_db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

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
    fun updateTask(taskEntry: TaskEntry)

    @Delete
    fun deleteTask(taskEntry: TaskEntry): Completable
}