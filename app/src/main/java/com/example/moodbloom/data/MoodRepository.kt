package com.example.moodbloom.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate


class MoodRepository(private val storage: FileStorage) {

    fun allEntries(): Flow<List<MoodEntry>> = storage.entries

    fun entriesInRange(start: LocalDate, endInclusive: LocalDate): Flow<List<MoodEntry>> =
        storage.entries.map { list ->
            list.filter { e ->
                val d = LocalDate.parse(e.dateIso)
                !d.isBefore(start) && !d.isAfter(endInclusive)
            }
        }

    suspend fun load() = storage.load()

    suspend fun upsert(date: LocalDate, family: MoodFamily, moodId: String) {
        storage.upsert(MoodEntry(date.toString(), family, moodId))
    }

    fun entryByDate(date: LocalDate): Flow<MoodEntry?> =
        storage.entries.map { it.find { e -> e.dateIso == date.toString() } }

    suspend fun delete(date: LocalDate) {
        storage.delete(date)
    }

}
