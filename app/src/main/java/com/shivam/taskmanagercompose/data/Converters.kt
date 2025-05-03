package com.shivam.taskmanagercompose.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val gson = Gson()
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    // LocalDateTime
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? {
        return dateTimeString?.let { LocalDateTime.parse(it, formatter) }
    }

    // List<String>
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    // List<Long>
    @TypeConverter
    fun fromLongList(list: List<Long>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toLongList(data: String): List<Long> {
        val listType = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(data, listType)
    }

    // SkillLevel Enum
    @TypeConverter
    fun fromSkillLevel(value: SkillLevel): String {
        return value.name
    }

    @TypeConverter
    fun toSkillLevel(value: String): SkillLevel {
        return SkillLevel.valueOf(value)
    }

    // GamePlatform Enum
    @TypeConverter
    fun fromGamePlatform(value: GamePlatform): String {
        return value.name
    }

    @TypeConverter
    fun toGamePlatform(value: String): GamePlatform {
        return GamePlatform.valueOf(value)
    }


    @TypeConverter
    fun fromPriority(priority: TaskPriority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): TaskPriority {
        return TaskPriority.valueOf(priority)
    }


} 