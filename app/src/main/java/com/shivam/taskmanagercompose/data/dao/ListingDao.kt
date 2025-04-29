package com.shivam.taskmanagercompose.data.dao

import androidx.room.*
import com.shivam.taskmanagercompose.data.GameListing
import kotlinx.coroutines.flow.Flow

@Dao
interface ListingDao {
    @Query("SELECT * FROM game_listings WHERE isActive = 1 ORDER BY createdAt DESC")
    fun getAllListings(): Flow<List<GameListing>>

    @Query("SELECT * FROM game_listings WHERE id = :id")
    fun getListingById(id: Long): Flow<GameListing?>

    @Query("SELECT * FROM game_listings WHERE id = :id")
    suspend fun getListingByIdSync(id: Long): GameListing?

    @Query("SELECT * FROM game_listings WHERE game = :game AND isActive = 1 ORDER BY createdAt DESC")
    fun getListingsByGame(game: String): Flow<List<GameListing>>

    @Query("SELECT * FROM game_listings WHERE creatorId = :creatorId ORDER BY createdAt DESC")
    fun getListingsByCreator(creatorId: Long): Flow<List<GameListing>>

    @Query("SELECT DISTINCT game FROM game_listings WHERE isActive = 1 ORDER BY game ASC")
    fun getAvailableGames(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListing(listing: GameListing): Long

    @Update
    suspend fun updateListing(listing: GameListing)

    @Query("UPDATE game_listings SET isActive = 0 WHERE id = :id")
    suspend fun deleteListing(id: Long)
} 