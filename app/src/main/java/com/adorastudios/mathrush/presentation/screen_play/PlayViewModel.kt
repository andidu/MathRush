package com.adorastudios.mathrush.presentation.screen_play

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adorastudios.mathrush.domain.RecordRepository
import com.adorastudios.mathrush.domain.Task
import com.adorastudios.mathrush.presentation.screen_main.GameType
import com.adorastudios.mathrush.presentation.utils.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TWENTY_SECONDS = 20 * 1000
private const val SECOND = 1000

@HiltViewModel
class PlayViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state: MutableState<PlayState> = mutableStateOf(PlayState())
    val state: State<PlayState> = _state

    private lateinit var task: Task

    private var updateJob: Job? = null
    private var _timeStart: Long? = null
    private val timeStart: Long
        get() = _timeStart!!

    private val _flow: MutableSharedFlow<PlayUiEvent> = MutableSharedFlow()
    val flow: SharedFlow<PlayUiEvent> = _flow.asSharedFlow()

    init {
        savedStateHandle.get<String>(Screens.TASK_NAME_PARAMETER)?.let { taskFull ->
            val taskId = taskFull.subSequence(0, taskFull.length - 4)
            val taskType = taskFull.subSequence(taskFull.length - 4, taskFull.length).run {
                when (this) {
                    RecordRepository.TIME -> GameType.Time
                    RecordRepository.BEST -> GameType.Best
                    else -> return@let
                }
            }
            Task.tasks.forEach {
                if (it.id == taskId) {
                    _state.value = state.value.copy(
                        taskData = TaskData(it, taskType),
                    )
                    task = it
                }
            }
        }

        if (this::task.isInitialized) {
            generateQuestion()
        }

        updateJob = viewModelScope.launch {
            _timeStart = System.currentTimeMillis()

            while (true) {
                val timeNow = System.currentTimeMillis()

                delay(16)

                val taskData = state.value.taskData ?: return@launch
                val realTime = when (taskData.type) {
                    GameType.Best -> TWENTY_SECONDS - (timeNow - timeStart)
                    GameType.Time -> timeNow - timeStart
                }
                if (taskData.type == GameType.Best) {
                    if (realTime <= 0) {
                        _state.value = state.value.copy(
                            time = String.format("00:00"),
                            finished = true,
                        )
                        updateJob?.cancel()
                        viewModelScope.launch {
                            _flow.emit(
                                PlayUiEvent.Finished(
                                    taskId = task.id + when (taskData.type) {
                                        GameType.Best -> RecordRepository.BEST
                                        GameType.Time -> RecordRepository.TIME
                                    },
                                    result = state.value.correctAmount.toLong(),
                                    success = true,
                                ),
                            )
                        }
                    }
                }
                _state.value = state.value.copy(
                    time = realTime.formatMillisAsTime(),
                )
            }
        }
    }

    private fun generateQuestion() {
        _state.value = state.value.copy(
            question = task.generate(),
        )
    }

    fun onEvent(event: PlayEvent) {
        if (state.value.finished || state.value.failed) return
        when (event) {
            PlayEvent.DeleteAll -> {
                _state.value = state.value.copy(
                    currentAnswer = "",
                    minus = false,
                )
            }

            PlayEvent.DeleteOne -> {
                if (state.value.currentAnswer.isNotEmpty()) {
                    _state.value = state.value.copy(
                        currentAnswer = state.value.currentAnswer.substring(
                            0,
                            state.value.currentAnswer.lastIndex,
                        ),
                    )
                } else {
                    _state.value = state.value.copy(
                        minus = false,
                    )
                }
            }

            PlayEvent.Minus -> {
                _state.value = state.value.copy(
                    minus = !state.value.minus,
                )
            }

            is PlayEvent.Number -> {
                _state.value = state.value.copy(
                    currentAnswer = state.value.currentAnswer + event.number,
                )
            }

            PlayEvent.Ok -> {
                val timeEnd = System.currentTimeMillis()
                val answer = state.value.currentAnswer.let {
                    if (state.value.minus) {
                        "-$it"
                    } else {
                        it
                    }
                }

                if (answer == "${state.value.question.second}") {
                    _state.value = state.value.copy(
                        correctAmount = state.value.correctAmount + 1,
                        minus = false,
                        currentAnswer = "",
                    )
                    val taskData = state.value.taskData ?: return
                    if (taskData.type == GameType.Time) {
                        if (state.value.correctAmount == 10) {
                            _state.value = state.value.copy(
                                finished = true,
                            )
                            updateJob?.cancel()
                            viewModelScope.launch {
                                _flow.emit(
                                    PlayUiEvent.Finished(
                                        taskId = task.id + when (taskData.type) {
                                            GameType.Best -> RecordRepository.BEST
                                            GameType.Time -> RecordRepository.TIME
                                        },
                                        result = timeEnd - timeStart,
                                        success = true,
                                    ),
                                )
                            }
                        } else {
                            generateQuestion()
                        }
                    } else {
                        generateQuestion()
                    }
                } else {
                    _state.value = state.value.copy(
                        failed = true,
                    )
                    viewModelScope.launch {
                        _flow.emit(
                            PlayUiEvent.Finished(
                                taskId = task.id + when (state.value.taskData?.type) {
                                    GameType.Best -> RecordRepository.BEST
                                    GameType.Time -> RecordRepository.TIME
                                    null -> return@launch
                                },
                                result = -1,
                                success = false,
                            ),
                        )
                    }
                }
            }
        }
    }
}

fun Long.formatMillisAsTime(): String {
    val time = this.coerceAtLeast(0L)
    return String.format("%02d", time / SECOND) + ":" +
        String.format("%02d", time / 10 % 100)
}
