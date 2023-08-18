package com.adorastudios.mathrush.presentation.screen_main.top_bar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.roundToInt

val MinTopBarHeight = 56.dp
val MaxTopBarHeight = 300.dp

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    progress: Float,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        val placeables = measurables.map {
            it.measure(constraints)
        }
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            val verticalGuideline = (constraints.maxWidth * 0.5f).roundToInt()
            val leftGuideline = (constraints.maxWidth * 0.25f).roundToInt()
            val rightGuideline = (constraints.maxWidth * 0.75f).roundToInt()

            val title = placeables[0]
            val best = placeables[1]
            val time = placeables[2]

            title.placeRelative(
                x = lerp(
                    start = 0,
                    stop = verticalGuideline - title.width / 2,
                    fraction = progress,
                ),
                y = lerp(
                    start = MinTopBarHeight.roundToPx() / 2 - title.height / 2,
                    stop = MaxTopBarHeight.roundToPx() / 2 - title.height / 2,
                    fraction = progress,
                ),
            )
            best.placeRelative(
                x = lerp(
                    start = constraints.maxWidth - time.width - best.width -
                        8.dp.toPx().roundToInt(),
                    stop = leftGuideline - best.width / 2,
                    fraction = progress,
                ),
                y = lerp(
                    start = MinTopBarHeight.roundToPx() / 2 - best.height / 2,
                    stop = MaxTopBarHeight.roundToPx() - MinTopBarHeight.roundToPx() / 2 - title.height / 2,
                    fraction = progress,
                ),
            )
            time.placeRelative(
                x = lerp(
                    start = constraints.maxWidth - time.width,
                    stop = rightGuideline - time.width / 2,
                    fraction = progress,
                ),
                y = lerp(
                    start = MinTopBarHeight.roundToPx() / 2 - time.height / 2,
                    stop = MaxTopBarHeight.roundToPx() - MinTopBarHeight.roundToPx() / 2 - title.height / 2,
                    fraction = progress,
                ),
            )
        }
    }
}
