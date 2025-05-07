package com.shivam.taskmanagercompose.di

import android.content.Context
import androidx.room.Room
import com.shivam.taskmanagercompose.data.TaskDatabase
import com.shivam.taskmanagercompose.data.TaskDao
import com.shivam.taskmanagercompose.data.dao.ListingDao
import com.shivam.taskmanagercompose.data.repository.ListingRepository
import com.shivam.taskmanagercompose.data.repository.RoomListingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(
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
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }
    
    @Provides
    @Singleton
    fun provideListingDao(database: TaskDatabase): ListingDao {
        return database.listingDao()
    }

    @Provides
    @Singleton
    fun provideListingRepository(listingDao: ListingDao): ListingRepository {
        return RoomListingRepository(listingDao)
    }
} 