package com.adorastudios.mathrush.presentation.screen_play

sealed class PlayUiEvent {
    data class Finished(val taskId: String, val result: Long, val success: Boolean): PlayUiEvent()
}
