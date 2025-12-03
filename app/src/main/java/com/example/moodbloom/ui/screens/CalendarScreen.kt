package com.example.moodbloom.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodbloom.R
import com.example.moodbloom.data.MoodEntry
import com.example.moodbloom.data.MoodFamily
import com.example.moodbloom.ui.GradientBackground
import com.example.moodbloom.ui.components.GlassButton
import com.example.moodbloom.vm.CalendarViewModel
import com.example.moodbloom.vm.SimpleVmFactory
import java.time.LocalDate

private fun familyDrawable(f: MoodFamily) = when (f) {
    MoodFamily.ANGER    -> R.drawable.anger
    MoodFamily.FEAR     -> R.drawable.fear
    MoodFamily.JOY      -> R.drawable.joy
    MoodFamily.LOVE     -> R.drawable.love
    MoodFamily.SADNESS  -> R.drawable.sadness
    MoodFamily.SURPRISE -> R.drawable.surprise
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    onBack: () -> Unit,
    onPickDay: (String) -> Unit,
    vm: CalendarViewModel = viewModel(factory = SimpleVmFactory.default())
) {
    val state by vm.state.collectAsState()
    val today = remember { LocalDate.now() }

    var selectedEntry by remember { mutableStateOf<MoodEntry?>(null) }
    var selectedDay by remember { mutableStateOf<LocalDate?>(null) }

    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("${state.month.month} ${state.month.year}") },
                    actions = {
                        IconButton(onClick = { vm.prev() }) {
                            Icon(Icons.Default.ChevronLeft, contentDescription = "Previous month")
                        }
                        IconButton(onClick = { vm.next() }) {
                            Icon(Icons.Default.ChevronRight, contentDescription = "Next month")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )

            }
        ) { pad ->
            Box(
                modifier = Modifier
                    .padding(pad)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 16.dp)
                ) {
                    // Weekday labels
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT").forEach { label ->
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }


                    LazyVerticalGrid(
                        columns = GridCells.Fixed(7),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 96.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.days.size) { i ->
                            val d = state.days[i]
                            val inMonth = d.month == state.month.month
                            val todayDate = LocalDate.now()
                            val isFuture = d.isAfter(todayDate)

                            val entry = state.entries.firstOrNull { it.dateIso == d.toString() }

                            val showFlower = entry != null
                            val showPlus = entry == null && !isFuture && inMonth

                            val bgColor = when {
                                showFlower -> Color.Transparent
                                showPlus -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.18f)
                                else -> {
                                    if (inMonth)
                                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.30f)
                                    else
                                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.10f)
                                }
                            }

                            val textColor = when {
                                showFlower -> MaterialTheme.colorScheme.onSurface
                                showPlus   -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.90f)
                                isFuture   -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.40f)
                                else       -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.60f)
                            }

                            Column(
                                modifier = Modifier.width(44.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(bgColor)
                                        .clickable(enabled = showFlower || showPlus) {
                                            when {
                                                showFlower -> {
                                                    selectedEntry = entry
                                                    selectedDay = d
                                                }
                                                showPlus -> onPickDay(d.toString())
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    when {
                                        showFlower -> {
                                            Image(
                                                painter = painterResource(familyDrawable(entry!!.family)),
                                                contentDescription = entry.family.name,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                        showPlus -> {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "Log mood",
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = d.dayOfMonth.toString(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = textColor
                                )
                            }

                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlassButton(onClick = { onBack() }) {     // back to Home / bouquet
                        Icon(Icons.Default.Spa, contentDescription = "Bouquet")
                    }
                    GlassButton(onClick = { /* already here */ }, selected = true) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = "Calendar")
                    }
                    GlassButton(onClick = { onPickDay(LocalDate.now().toString()) }) {
                        Icon(Icons.Default.Add, contentDescription = "Log mood")
                    }
                }

                selectedEntry?.let { entry ->
                    val day = selectedDay
                    if (day != null) {
                        AlertDialog(
                            onDismissRequest = {
                                selectedEntry = null
                                selectedDay = null
                            },
                            title = { Text("Mood for $day") },
                            text = { Text("What would you like to do?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    onPickDay(day.toString())
                                    selectedEntry = null
                                    selectedDay = null
                                }) { Text("Edit mood") }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    vm.delete(day)
                                    selectedEntry = null
                                    selectedDay = null
                                }) { Text("Delete") }
                            }
                        )
                    }
                }
            }
        }
    }
}
