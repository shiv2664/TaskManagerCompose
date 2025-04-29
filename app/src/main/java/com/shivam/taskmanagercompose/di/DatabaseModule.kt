package com.shivam.taskmanagercompose.di

import android.content.Context
import androidx.room.Room
import com.shivam.taskmanagercompose.data.AppDatabase
import com.shivam.taskmanagercompose.data.dao.ListingDao
import com.shivam.taskmanagercompose.data.dao.ProfileDao
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
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "buddy_finder_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideListingDao(database: AppDatabase): ListingDao {
        return database.listingDao()
    }

    @Provides
    @Singleton
    fun provideProfileDao(database: AppDatabase): ProfileDao {
        return database.profileDao()
    }

    @Provides
    @Singleton
    fun provideListingRepository(listingDao: ListingDao): ListingRepository {
        return RoomListingRepository(listingDao)
    }
} 