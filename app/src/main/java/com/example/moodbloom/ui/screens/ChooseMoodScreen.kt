package com.example.moodbloom.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodbloom.data.MoodFamily
import com.example.moodbloom.ui.GradientBackground
import com.example.moodbloom.ui.components.GlassButton
import com.example.moodbloom.vm.ChooseMoodViewModel
import com.example.moodbloom.vm.SimpleVmFactory
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseMoodScreen(
    familyName: String,
    dateIso: String,
    onDone: () -> Unit,
    onBack: () -> Unit,
    vm: ChooseMoodViewModel = viewModel(factory = SimpleVmFactory.default())
) {
    val fam = MoodFamily.valueOf(familyName)

    LaunchedEffect(fam, dateIso) {
        vm.init(family = fam, date = LocalDate.parse(dateIso))
    }

    val state by vm.state.collectAsState()

    val config = LocalConfiguration.current
    val columns = if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2

    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Choose a mood.") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            if (state == null) {
                Box(
                    modifier = Modifier
                        .padding(pad)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                return@Scaffold
            }

            val s = state!!

            Column(
                modifier = Modifier
                    .padding(pad)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(s.moods.size) { index ->
                        val mood = s.moods[index]
                        GlassButton(
                            onClick = { vm.select(mood.id, onDone) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    mood.label,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                TextButton(
                    onClick = {
                        vm.delete(LocalDate.parse(dateIso))
                        onDone()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Remove mood", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
