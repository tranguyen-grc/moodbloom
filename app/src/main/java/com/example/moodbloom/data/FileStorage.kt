package com.example.moodbloom.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.File

class FileStorage(private val context: Context) {
    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }
    private val file: File get() = File(context.filesDir, "mood_entries.json")

    private val _entries = MutableStateFlow<List<MoodEntry>>(emptyList())
    val entries: StateFlow<List<MoodEntry>> = _entries

    suspend fun load() = withContext(Dispatchers.IO) {
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            file.writeText("[]")
        }
        val text = file.readText()
        val list = json.decodeFromString(ListSerializer(MoodEntry.serializer()), text)
        _entries.value = list
    }

    suspend fun upsert(entry: MoodEntry) = withContext(Dispatchers.IO) {
        val existing: List<MoodEntry> =
            if (file.exists()) {
                val text = file.readText()
                if (text.isBlank()) emptyList()
                else json.decodeFromString(ListSerializer(MoodEntry.serializer()), text)
            } else {
                emptyList()
            }

        val current = existing.toMutableList()
        val idx = current.indexOfFirst { it.dateIso == entry.dateIso }
        if (idx >= 0) current[idx] = entry else current += entry

        _entries.value = current
        val text = json.encodeToString(ListSerializer(MoodEntry.serializer()), current)
        file.writeText(text)
    }


    suspend fun delete(date: java.time.LocalDate) = withContext(Dispatchers.IO) {
        val newList = _entries.value.filterNot { it.dateIso == date.toString() }
        _entries.value = newList
        val text = json.encodeToString(ListSerializer(MoodEntry.serializer()), newList)
        file.writeText(text)
    }

}
