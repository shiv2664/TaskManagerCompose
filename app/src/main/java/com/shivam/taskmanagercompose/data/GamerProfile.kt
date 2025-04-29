package com.shivam.taskmanagercompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "gamer_profiles")
data class GamerProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val displayName: String,
    val bio: String,
    val favoriteGames: List<String>,
    val skillLevel: SkillLevel,
    val availability: List<String>,
    val profileImageUrl: String?,
    val discordUsername: String?,
    val steamId: String?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val lastActive: LocalDateTime = LocalDateTime.now()
)

enum class SkillLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    PROFESSIONAL
} 