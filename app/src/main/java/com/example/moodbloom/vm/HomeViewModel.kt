package com.example.moodbloom.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.data.MoodEntry
import com.example.moodbloom.data.MoodRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

data class HomeState(
    val weekStart: LocalDate = LocalDate.now().with(DayOfWeek.MONDAY),
    val entries: List<MoodEntry> = emptyList(),
    val todayLogged: Boolean = false,
    val moodWords: String = ""
)

class HomeViewModel(private val repo: MoodRepository) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        viewModelScope.launch { repo.load() }
        refreshWeek()
    }

    fun refreshWeek(weekStart: LocalDate = LocalDate.now().with(DayOfWeek.MONDAY)) {
        viewModelScope.launch {
            repo.entriesInRange(weekStart, weekStart.plusDays(6))
                .combine(repo.entryByDate(LocalDate.now())) { list, today ->
                    Triple(weekStart, list, today != null)
                }
                .collect { (ws, list, todayLogged) ->
                    val words = buildMoodWords(list)
                    _state.value = HomeState(
                        weekStart = ws,
                        entries = list,
                        todayLogged = todayLogged,
                        moodWords = words
                    )
                }
        }
    }

    private fun buildMoodWords(entries: List<MoodEntry>): String {
        val labels: List<String> = entries
            .map { moodLabelForId(it.moodId) }
            .distinct()

        if (labels.isEmpty()) return ""

        val half = (labels.size + 1) / 2
        val line1 = labels.take(half).joinToString("  ")
        val line2 = labels.drop(half).joinToString("  ")

        return listOf(line1, line2)
            .filter { it.isNotBlank() }
            .joinToString("\n")
    }

    private fun moodLabelForId(id: String): String {
        val mood = com.example.moodbloom.data.MoodsCatalog.moodsByFamily
            .values
            .flatten()
            .find { it.id == id }

        return mood?.label ?: id
    }

}
