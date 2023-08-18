package com.adorastudios.mathrush.presentation.screen_play

sealed class PlayEvent {
    data class Number(val number: String) : PlayEvent()
    object Minus : PlayEvent()
    object Ok : PlayEvent()
    object DeleteOne : PlayEvent()
    object DeleteAll : PlayEvent()
}
