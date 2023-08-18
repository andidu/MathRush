package com.adorastudios.mathrush.presentation.screen_main.top_bar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.rememberSaveable

@Stable
interface TopBarState {
    val height: Float
    val progress: Float
    var scrollValue: Int
}

@Composable
fun rememberTopBarState(topBarHeightRange: IntRange, scrollValue: Int = 0): TopBarState {
    return rememberSaveable(saver = TopBarStateImpl.Saver) {
        TopBarStateImpl(topBarHeightRange, scrollValue)
    }
}
