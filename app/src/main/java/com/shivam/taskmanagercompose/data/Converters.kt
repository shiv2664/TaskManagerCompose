package com.shivam.taskmanagercompose.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun fromStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromLongList(value: String): List<Long> {
        val listType = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toLongList(list: List<Long>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromSkillLevel(value: SkillLevel): String {
        return value.name
    }

    @TypeConverter
    fun toSkillLevel(value: String): SkillLevel {
        return SkillLevel.valueOf(value)
    }

    @TypeConverter
    fun fromGamePlatform(value: GamePlatform): String {
        return value.name
    }

    @TypeConverter
    fun toGamePlatform(value: String): GamePlatform {
        return GamePlatform.valueOf(value)
    }
} 