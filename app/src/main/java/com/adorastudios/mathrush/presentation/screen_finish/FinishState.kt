package com.adorastudios.mathrush.presentation.screen_finish

import com.adorastudios.mathrush.presentation.screen_play.TaskData

sealed class FinishState {
    object Loading : FinishState()

    sealed class Loaded(
        open val taskData: TaskData,
    ) : FinishState() {
        data class Success(
            override val taskData: TaskData,
            val result: Long,
            val prevBest: Long,
        ) : Loaded(taskData)

        data class Failure(
            override val taskData: TaskData,
        ) : Loaded(taskData)
    }
}
