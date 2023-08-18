package com.adorastudios.mathrush.presentation.screen_finish

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adorastudios.mathrush.domain.RecordRepository
import com.adorastudios.mathrush.domain.Task
import com.adorastudios.mathrush.presentation.screen_main.GameType
import com.adorastudios.mathrush.presentation.screen_play.TaskData
import com.adorastudios.mathrush.presentation.utils.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinishViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    recordRepository: RecordRepository,
) : ViewModel() {

    private val _state: MutableState<FinishState> = mutableStateOf(FinishState.Loading)
    val state: State<FinishState> = _state

    init {
        val taskName = savedStateHandle.get<String>(Screens.TASK_NAME_PARAMETER)
        val taskResult = savedStateHandle.get<Long>(Screens.TASK_RESULT_PARAMETER)
        val taskSuccess = savedStateHandle.get<Boolean>(Screens.TASK_SUCCESS_PARAMETER)

        if (taskName != null && taskResult != null && taskSuccess != null) {
            val taskId = taskName.subSequence(0, taskName.length - 4)
            val taskType = taskName.subSequence(taskName.length - 4, taskName.length).run {
                if (this == RecordRepository.TIME) {
                    GameType.Time
                } else {
                    GameType.Best
                }
            }

            var task: Task? = null
            Task.tasks.forEach {
                if (it.id == taskId) {
                    task = it
                }
            }

            _state.value = if (taskSuccess) {
                val prevBest = recordRepository.getRecord(task!!.id)

                when (taskType) {
                    GameType.Best -> {
                        if (taskResult > prevBest) {
                            viewModelScope.launch(Dispatchers.IO) {
                                recordRepository.setRecord(taskName, taskResult)
                            }
                        }

                        FinishState.Loaded.Success(
                            taskData = TaskData(task!!, taskType),
                            result = taskResult,
                            prevBest = prevBest,
                        )
                    }

                    GameType.Time -> {
                        if (taskResult < prevBest || prevBest == -1L) {
                            viewModelScope.launch(Dispatchers.IO) {
                                recordRepository.setRecord(taskName, taskResult)
                            }
                        }

                        FinishState.Loaded.Success(
                            taskData = TaskData(task!!, taskType),
                            result = taskResult,
                            prevBest = prevBest,
                        )
                    }
                }
            } else {
                FinishState.Loaded.Failure(
                    taskData = TaskData(task!!, taskType),
                )
            }
        }
    }
}
