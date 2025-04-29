package com.shivam.taskmanagercompose.data.repository

import com.shivam.taskmanagercompose.data.GameListing
import com.shivam.taskmanagercompose.data.dao.ListingDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomListingRepository @Inject constructor(
    private val listingDao: ListingDao
) : ListingRepository {
    override fun getAllListings(): Flow<List<GameListing>> =
        listingDao.getAllListings()

    override fun getListingById(id: Long): Flow<GameListing?> =
        listingDao.getListingById(id)

    override fun getListingsByGame(game: String): Flow<List<GameListing>> =
        listingDao.getListingsByGame(game)

    override fun getListingsByCreator(creatorId: Long): Flow<List<GameListing>> =
        listingDao.getListingsByCreator(creatorId)

    override fun getAvailableGames(): Flow<List<String>> =
        listingDao.getAvailableGames()

    override suspend fun createListing(listing: GameListing): Long =
        listingDao.insertListing(listing)

    override suspend fun updateListing(listing: GameListing) =
        listingDao.updateListing(listing)

    override suspend fun deleteListing(id: Long) =
        listingDao.deleteListing(id)

    override suspend fun joinListing(listingId: Long) {
        val listing = listingDao.getListingByIdSync(listingId) ?: return
        // TODO: Get current user ID from UserManager
        val currentUserId = 0L
        if (!listing.playersJoined.contains(currentUserId)) {
            val updatedListing = listing.copy(
                playersJoined = listing.playersJoined + currentUserId
            )
            listingDao.updateListing(updatedListing)
        }
    }

    override suspend fun leaveListing(listingId: Long) {
        val listing = listingDao.getListingByIdSync(listingId) ?: return
        // TODO: Get current user ID from UserManager
        val currentUserId = 0L
        if (listing.playersJoined.contains(currentUserId)) {
            val updatedListing = listing.copy(
                playersJoined = listing.playersJoined - currentUserId
            )
            listingDao.updateListing(updatedListing)
        }
    }
} 