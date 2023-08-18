package com.adorastudios.mathrush.presentation.screen_main

sealed class MainEvent {
    data class GameTypeChanged(val gameType: GameType) : MainEvent()
    data class FilterChanged(val string: String?) : MainEvent()
}
