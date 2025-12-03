package com.example.moodbloom.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.moodbloom.R
import com.example.moodbloom.data.MoodEntry
import com.example.moodbloom.data.MoodFamily
import kotlin.math.roundToInt

private data class Slot(
    val x: Float,
    val y: Float,
    val scale: Float = 1f,
    val z: Int = 0
)

private val flowerSlots = listOf(
    Slot(0.5f, 0.0f, 1.00f, z = 3), // top center
    Slot(0.2f, 0.2f, 0.95f, z = 2),
    Slot(0.8f, 0.2f, 0.95f, z = 2),
    Slot(0.3f, 0.5f, 0.90f, z = 2),
    Slot(0.7f, 0.5f, 0.90f, z = 3),
    Slot(0.5f, 0.35f, 0.85f, z = 4),
)

private fun familyDrawable(f: MoodFamily) = when (f) {
    MoodFamily.ANGER    -> R.drawable.anger
    MoodFamily.FEAR     -> R.drawable.fear
    MoodFamily.JOY      -> R.drawable.joy
    MoodFamily.LOVE     -> R.drawable.love
    MoodFamily.SADNESS  -> R.drawable.sadness
    MoodFamily.SURPRISE -> R.drawable.surprise
}


@Composable
fun Bouquet(
    entries: List<MoodEntry>,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    // Debug bouquet
    val flowersToDraw = entries.ifEmpty {
        listOf(
            MoodEntry("2025-01-01", MoodFamily.LOVE, "love"),
            MoodEntry("2025-01-02", MoodFamily.ANGER, "anger"),
            MoodEntry("2025-01-03", MoodFamily.JOY, "joy"),
            MoodEntry("2025-01-04", MoodFamily.SURPRISE, "surprise"),
            MoodEntry("2025-01-05", MoodFamily.SADNESS, "sadness"),
            MoodEntry("2025-01-06", MoodFamily.FEAR, "fear"),
        )
    }

    BoxWithConstraints(
        modifier = modifier,
    ) {
        val w = constraints.maxWidth.toFloat()
        val h = constraints.maxHeight.toFloat()
        val centerX = w * 0.5f

        val bottleHeight = h * 0.65f
        val bottleWidth = bottleHeight * (441f / 889f)

        val bottleWidthFraction = (bottleWidth / w).coerceIn(0f, 1f)

        val baseWidth = bottleWidth * 2.15f
        val baseWidthFraction = (baseWidth / w).coerceIn(0f, 1f)


        val flowerTopY = h - bottleHeight * 1.5f
        val flowerBottomY = h - bottleHeight * 0.7f
        val flowerHeight = flowerBottomY - flowerTopY

        val flowerLeftX = w * 0.25f
        val flowerRightX = w * 0.75f
        val flowerWidth = flowerRightX - flowerLeftX

        // bottle
        Image(
            painter = painterResource(R.drawable.bottle),
            contentDescription = "Bottle",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(bottleWidthFraction)
                .aspectRatio(441f / 889f),
            contentScale = ContentScale.Fit
        )




        // stems + flowers
        if (flowersToDraw.isNotEmpty()) {
            val used = flowersToDraw.take(flowerSlots.size)

            data class Pos(val entry: MoodEntry, val slot: Slot, val center: Offset)

            val positions = used.zip(flowerSlots).map { (entry, slot) ->
                val cx = flowerLeftX + slot.x * flowerWidth
                val cy = flowerTopY + slot.y * flowerHeight
                Pos(entry, slot, Offset(cx, cy))
            }

            // stems
            Canvas(modifier = Modifier.fillMaxSize()) {
                val stemAnchor = Offset(
                    x = centerX,
                    y = flowerBottomY + bottleHeight * 0.6f
                )

                positions.forEach { pos ->
                    val end = Offset(
                        x = pos.center.x,
                        y = pos.center.y + bottleHeight * 0.02f
                    )

                    positions.forEachIndexed { index, pos ->
                        val end = Offset(
                            x = pos.center.x,
                            y = pos.center.y + bottleHeight * 0.02f
                        )

                        val midY = (stemAnchor.y + end.y) / 2f

                        val sway = (end.x - stemAnchor.x) * 0.2f

                        val cp1 = Offset(
                            x = stemAnchor.x + sway,
                            y = midY - bottleHeight * 0.05f
                        )

                        val cp2 = Offset(
                            x = stemAnchor.x + sway * 0.8f,
                            y = midY + bottleHeight * 0.05f
                        )

                        val path = Path().apply {
                            moveTo(stemAnchor.x, stemAnchor.y)
                            cubicTo(cp1.x, cp1.y, cp2.x, cp2.y, end.x, end.y)
                        }

                        drawPath(
                            path = path,
                            color = Color(0xFF2F7A4B),
                            style = Stroke(width = (size.minDimension * 0.01f).coerceAtLeast(2f))
                        )

                        drawPath(
                            path = path,
                            color = Color(0x662F7A4B),
                            style = Stroke(width = (size.minDimension * 0.006f).coerceAtLeast(1.2f))
                        )
                    }

                }
            }

            // flowers
            positions.sortedBy { it.slot.z }.forEach { pos ->
                val sizePx = (h * 0.22f * pos.slot.scale).coerceAtLeast(48f)
                val sizeDp = with(density) { sizePx.toDp() }
                val offset = IntOffset(
                    (pos.center.x - sizePx / 2f).roundToInt(),
                    (pos.center.y - sizePx / 2f).roundToInt()
                )

                Image(
                    painter = painterResource(familyDrawable(pos.entry.family)),
                    contentDescription = pos.entry.family.name,
                    modifier = Modifier
                        .size(sizeDp)
                        .offset { offset },
                    contentScale = ContentScale.Fit
                )
            }
        }
        // base plate + shine
        Image(
            painter = painterResource(R.drawable.base_shine),
            contentDescription = "base",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(baseWidthFraction)
                .aspectRatio(1119f / 484f),
            contentScale = ContentScale.Fit
        )

    }
}
