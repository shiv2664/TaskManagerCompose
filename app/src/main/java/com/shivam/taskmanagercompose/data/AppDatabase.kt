package com.shivam.taskmanagercompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shivam.taskmanagercompose.data.dao.ListingDao
import com.shivam.taskmanagercompose.data.dao.ProfileDao

@Database(
    entities = [
        GamerProfile::class,
        GameListing::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listingDao(): ListingDao
    abstract fun profileDao(): ProfileDao
} 