package com.example.moodbloom.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.data.Mood
import com.example.moodbloom.data.MoodFamily
import com.example.moodbloom.data.MoodRepository
import com.example.moodbloom.data.MoodsCatalog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

data class ChooseMoodState(
    val family: MoodFamily,
    val moods: List<Mood>,
    val targetDate: LocalDate
)

class ChooseMoodViewModel(private val repo: MoodRepository) : ViewModel() {

    private val _state = MutableStateFlow<ChooseMoodState?>(null)
    val state: StateFlow<ChooseMoodState?> = _state

    fun init(family: MoodFamily, date: LocalDate) {
        _state.value = ChooseMoodState(
            family = family,
            moods = MoodsCatalog.moodsByFamily[family] ?: emptyList(),
            targetDate = date
        )
    }

    fun select(moodId: String, onDone: () -> Unit) {
        viewModelScope.launch {
            val s = _state.value ?: return@launch
            repo.upsert(s.targetDate, s.family, moodId)
            onDone()
        }
    }

    fun delete(date: LocalDate) {
        viewModelScope.launch {
            repo.delete(date)
        }
    }

}
