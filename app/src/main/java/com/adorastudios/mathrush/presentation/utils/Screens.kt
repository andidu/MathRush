package com.adorastudios.mathrush.presentation.utils

sealed class Screens(val route: String) {
    object Main : Screens("main")
    object Play : Screens("play")
    object Result : Screens("result")

    companion object {
        const val TASK_NAME_PARAMETER = "task"
        const val TASK_RESULT_PARAMETER = "result"
        const val TASK_SUCCESS_PARAMETER = "success"

        fun toPlayScreen(task: String) = "${Play.route}?$TASK_NAME_PARAMETER=$task"
        fun toResultScreen(task: String, result: Long, success: Boolean) =
            "${Result.route}?$TASK_NAME_PARAMETER=$task" +
                "&$TASK_RESULT_PARAMETER=$result" +
                "&$TASK_SUCCESS_PARAMETER=$success"
    }
}
