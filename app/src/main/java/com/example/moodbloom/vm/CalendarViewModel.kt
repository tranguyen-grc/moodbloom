package com.example.moodbloom.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.data.MoodEntry
import com.example.moodbloom.data.MoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

data class CalendarState(
    val month: YearMonth = YearMonth.now(),
    val days: List<LocalDate> = emptyList(),
    val entries: List<MoodEntry> = emptyList()
)

class CalendarViewModel(private val repo: MoodRepository) : ViewModel() {

    private val _state = MutableStateFlow(CalendarState())
    val state: StateFlow<CalendarState> = _state

    init {
        viewModelScope.launch {
            repo.load()
            loadMonthInternal(YearMonth.now())
        }
    }

    fun loadMonth(month: YearMonth = state.value.month) {
        viewModelScope.launch {
            loadMonthInternal(month)
        }
    }

    private suspend fun loadMonthInternal(month: YearMonth) {
        val first = month.atDay(1)
        val last = month.atEndOfMonth()

        repo.entriesInRange(first, last).collect { entries ->
            val daysGrid = buildMonthGrid(month)

            _state.value = CalendarState(
                month = month,
                days = daysGrid,
                entries = entries
            )
        }
    }

    fun next() = loadMonth(state.value.month.plusMonths(1))
    fun prev() = loadMonth(state.value.month.minusMonths(1))

    fun delete(date: LocalDate) {
        viewModelScope.launch {
            repo.delete(date)
        }
    }
}

private fun buildMonthGrid(month: YearMonth): List<LocalDate> {
    val firstOfMonth = month.atDay(1)
    val firstDayOfWeekIndex = firstOfMonth.dayOfWeek.value % 7 // Sunday = 0
    val firstCellDate = firstOfMonth.minusDays(firstDayOfWeekIndex.toLong())
    return List(42) { i -> firstCellDate.plusDays(i.toLong()) }
}
