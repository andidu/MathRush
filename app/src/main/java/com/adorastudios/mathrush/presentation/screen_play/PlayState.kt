package com.adorastudios.mathrush.presentation.screen_play

import com.adorastudios.mathrush.domain.Task
import com.adorastudios.mathrush.presentation.screen_main.GameType

data class PlayState(
    val taskData: TaskData? = null,
    val question: Pair<String, Int> = "" to 0,
    val currentAnswer: String = "",
    val minus: Boolean = false,
    val time: String = "",
    val correctAmount: Int = 0,
    val failed: Boolean = false,
    val finished: Boolean = false,
)

data class TaskData(
    val task: Task,
    val type: GameType,
)
