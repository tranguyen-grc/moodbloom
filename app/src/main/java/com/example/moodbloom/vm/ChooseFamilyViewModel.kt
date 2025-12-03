package com.example.moodbloom.vm

import androidx.lifecycle.ViewModel
import com.example.moodbloom.data.MoodFamily
import java.time.LocalDate

data class ChooseFamilyState(
    val families: List<MoodFamily> = MoodFamily.entries,
    val targetDate: LocalDate = LocalDate.now()
)

class ChooseFamilyViewModel : ViewModel() {
    var state = ChooseFamilyState()
        private set

    fun init(date: LocalDate?) { state = state.copy(targetDate = date ?: LocalDate.now()) }
}
