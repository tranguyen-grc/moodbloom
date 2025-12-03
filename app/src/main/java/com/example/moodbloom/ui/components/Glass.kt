package com.example.moodbloom.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GlassButton(
    onClick: () -> Unit,
    selected: Boolean = false,
    content: @Composable () -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    val bg = if (selected) scheme.secondary.copy(alpha = 0.20f) else scheme.surface.copy(alpha = 0.20f)
    val border = if (selected) scheme.secondary.copy(alpha = 0.80f) else scheme.surface.copy(alpha = 0.20f)

    Surface(
        onClick = onClick,
        color = bg,
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (selected)
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.80f)
            else
                MaterialTheme.colorScheme.outline.copy(alpha = 0.20f)
        ),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) { ProvideTextStyle(MaterialTheme.typography.labelLarge) {
        androidx.compose.foundation.layout.Box(Modifier.padding(horizontal = 18.dp, vertical = 12.dp)) { content() }
    } }
}

@Composable
fun GlassChip(onClick: () -> Unit, label: String) {
    AssistChip(onClick = onClick, label = { Text(label) })
}
