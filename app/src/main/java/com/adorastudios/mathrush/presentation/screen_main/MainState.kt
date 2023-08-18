package com.adorastudios.mathrush.presentation.screen_main

import com.adorastudios.mathrush.domain.Task

data class MainState(
    val selectedOperation: String? = null,
    val operations: List<String> = listOf(),
    val gameType: GameType = GameType.Time,
    val tasks: List<Task> = Task.tasks,
)
