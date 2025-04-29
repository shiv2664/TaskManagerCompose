package com.shivam.taskmanagercompose.data.repository

import com.shivam.taskmanagercompose.data.GameListing
import kotlinx.coroutines.flow.Flow

interface ListingRepository {
    fun getAllListings(): Flow<List<GameListing>>
    fun getListingById(id: Long): Flow<GameListing?>
    fun getListingsByGame(game: String): Flow<List<GameListing>>
    fun getListingsByCreator(creatorId: Long): Flow<List<GameListing>>
    fun getAvailableGames(): Flow<List<String>>
    
    suspend fun createListing(listing: GameListing): Long
    suspend fun updateListing(listing: GameListing)
    suspend fun deleteListing(id: Long)
    suspend fun joinListing(listingId: Long)
    suspend fun leaveListing(listingId: Long)
} 