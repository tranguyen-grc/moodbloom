package com.example.moodbloom.data

import kotlinx.serialization.Serializable

enum class MoodFamily { LOVE, ANGER, JOY, SURPRISE, SADNESS, FEAR }

@Serializable
data class Mood(val id: String, val label: String, val family: MoodFamily)

@Serializable
data class MoodEntry(
    val dateIso: String,        // ISO yyyy-MM-dd for storage
    val family: MoodFamily,
    val moodId: String
)