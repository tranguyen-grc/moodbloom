package com.example.moodbloom.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.moodbloom.data.FileStorage
import com.example.moodbloom.data.MoodRepository

@Suppress("UNCHECKED_CAST")
class SimpleVmFactory(private val repo: MoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(repo) as T
            CalendarViewModel::class.java -> CalendarViewModel(repo) as T
            ChooseMoodViewModel::class.java -> ChooseMoodViewModel(repo) as T
            else -> throw IllegalArgumentException("Unknown VM")
        }
    }

    companion object {
        @Composable
        fun forChooseMood(): ViewModelProvider.Factory {
            val ctx = LocalContext.current.applicationContext
            val repo = MoodRepository(FileStorage(ctx))
            return SimpleVmFactory(repo)
        }
        @Composable
        fun default(): ViewModelProvider.Factory {
            val ctx = LocalContext.current.applicationContext
//            val repo = MoodRepository(FileStorage(ctx))
            val repo = remember { MoodRepository(FileStorage(ctx)) }
            return SimpleVmFactory(repo)
        }
    }
}
