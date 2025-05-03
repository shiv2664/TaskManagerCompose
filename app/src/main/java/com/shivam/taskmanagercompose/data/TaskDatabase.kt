package com.shivam.taskmanagercompose.data

import android.content.Context
import androidx.room.*
import java.time.LocalDateTime
import javax.inject.Singleton

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}