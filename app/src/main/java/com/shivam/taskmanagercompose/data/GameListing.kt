package com.shivam.taskmanagercompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "game_listings")
data class GameListing(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val creatorId: Long,
    val game: String,
    val title: String,
    val description: String,
    val requiredSkillLevel: SkillLevel,
    val playersNeeded: Int,
    val playersJoined: List<Long>, // List of user IDs
    val scheduledTime: LocalDateTime?,
    val platform: GamePlatform,
    val tags: List<String>,
    val isActive: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class GamePlatform {
    PC,
    PLAYSTATION,
    XBOX,
    NINTENDO_SWITCH,
    MOBILE,
    ANY
} 