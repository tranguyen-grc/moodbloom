package com.example.moodbloom.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodbloom.ui.GradientBackground
import com.example.moodbloom.ui.components.Bouquet
import com.example.moodbloom.ui.components.GlassButton
import com.example.moodbloom.vm.HomeViewModel
import com.example.moodbloom.vm.SimpleVmFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCalendar: () -> Unit,
    onLogMood: () -> Unit,
    vm: HomeViewModel = viewModel(factory = SimpleVmFactory.default())
) {
    val state by vm.state.collectAsState()

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Hello, Jane.",
                            fontSize = 26.sp
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = if(isLandscape) {
                        Modifier.padding(top = 16.dp, start = 24.dp)
                    } else {
                        Modifier.padding(top = 80.dp, start = 24.dp)
                    }

                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                if (!isLandscape) {
                    // PORTRAIT: bouquet above words
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(bottom = 90.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Bouquet(
                            entries = state.entries,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                        )

                        Spacer(Modifier.height(12.dp))

                        if (state.moodWords.isNotBlank()) {
                            Text(
                                state.moodWords,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                                lineHeight = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(end = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Bouquet(
                                entries = state.entries,
                                modifier = Modifier
                                    .fillMaxHeight(0.7f)  // 👈 big in landscape
                                    .fillMaxWidth()
                            )
                        }

                        if (state.moodWords.isNotBlank()) {
                            Text(
                                state.moodWords,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                                lineHeight = 18.sp,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp)
                            )
                        } else {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlassButton(onClick = { /* bouquet view */ }, selected = true) {
                        Icon(Icons.Default.Spa, contentDescription = null)
                    }
                    GlassButton(onClick = onCalendar) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null)
                    }
                    GlassButton(onClick = onLogMood) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }
            }
        }
    }
}
