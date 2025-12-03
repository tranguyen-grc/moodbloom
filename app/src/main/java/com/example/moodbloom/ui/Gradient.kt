package com.example.moodbloom.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.moodbloom.ui.theme.*

@Composable
fun GradientBackground(
    dark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val brush = if (dark) {
        Brush.verticalGradient(listOf(DarkBgTop, DarkBgBottom))
    } else {
        Brush.verticalGradient(listOf(LightBgTop, LightBgBottom))
    }
    Box(Modifier.fillMaxSize().background(brush)) { content() }
}
