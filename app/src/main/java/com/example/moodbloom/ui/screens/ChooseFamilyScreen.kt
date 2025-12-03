package com.example.moodbloom.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.moodbloom.R
import com.example.moodbloom.data.MoodFamily
import com.example.moodbloom.ui.GradientBackground

// same mapping used elsewhere
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
fun ChooseFamilyScreen(
    dateIso: String,
    onPickFamily: (MoodFamily) -> Unit,
    onBack: () -> Unit
) {
    val config = LocalConfiguration.current
    val columns = if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2

    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    title = { Text("Choose a mood family.") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                )
            }
        ) { pad ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier
                    .padding(pad)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(MoodFamily.entries.size) { index ->
                    val family = MoodFamily.entries[index]

                    Card(
                        onClick = { onPickFamily(family) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 16.dp, horizontal = 8.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(familyDrawable(family)),
                                contentDescription = family.name,
                                modifier = Modifier.size(72.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = family.name.lowercase().replaceFirstChar { it.titlecase() },
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
