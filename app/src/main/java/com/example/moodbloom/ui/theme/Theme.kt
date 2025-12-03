package com.example.moodbloom.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.googlefonts.GoogleFont

private val lightColors = lightColorScheme(
    primary = LightPrimary,
    onPrimary = Color.White,
    secondary = LightSecondary,
    onSecondary = Color.White,
    background = Color.Transparent,
    surface = Color(0xFFFFFFFF),
    onSurface = LightPrimary
)

private val darkColors = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = Color.Black,
    secondary = DarkSecondary,
    onSecondary = Color.Black,
    background = Color.Transparent,
    surface = Color(0xFF0F1D26),
    onSurface = DarkPrimary
)


@Composable
fun MoodBloomTheme(useDark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (useDark) darkColors else lightColors,
        typography  = Typography,
        content     = content
    )
}