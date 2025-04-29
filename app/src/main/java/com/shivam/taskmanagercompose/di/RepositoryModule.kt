package com.shivam.taskmanagercompose.di

import android.content.Context
import androidx.room.Room
import com.shivam.taskmanagercompose.data.TaskDatabase
import com.shivam.taskmanagercompose.data.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context
    ): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(database: TaskDatabase): TaskRepository {
        return TaskRepository(database.taskDao())
    }
} 