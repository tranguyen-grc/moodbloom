package com.example.moodbloom.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.abs

private fun stemColor() = Color(0xFF2F7A4B)
private fun stemHighlight() = Color(0x662F7A4B)


@androidx.compose.runtime.Composable
fun StemsLayer(
    ends: List<Pair<Float, Float>>,
    vaseAnchor: Pair<Float, Float> = 0.50f to 0.78f,
    modifier: Modifier = Modifier
) {
    Canvas(modifier) {
        val anchor = Offset(size.width * vaseAnchor.first, size.height * vaseAnchor.second)
        ends.forEachIndexed { idx, (xPct, yPct) ->
            val end = Offset(size.width * xPct, size.height * yPct)

            val midY = (anchor.y + end.y) / 2f
            val lateral = (end.x - anchor.x)
            val sway = lateral * 0.35f
            val cp1 = Offset(anchor.x + sway * 0.30f, midY + (idx % 2 - 0.5f) * 12f)
            val cp2 = Offset(anchor.x + sway * 0.80f, midY - (idx % 2 - 0.5f) * 12f)

            val path = Path().apply {
                moveTo(anchor.x, anchor.y)
                cubicTo(cp1.x, cp1.y, cp2.x, cp2.y, end.x, end.y)
            }

            val dist = abs(end.x - anchor.x) + abs(end.y - anchor.y)
            val base = (size.minDimension * 0.010f).coerceAtLeast(2f)
            val width = (base + dist * 0.0015f).coerceAtMost(base * 2.2f)

            drawPath(path, color = stemColor(),     style = Stroke(width = width))
            drawPath(path, color = stemHighlight(), style = Stroke(width = width * 0.6f))
        }
    }
}
