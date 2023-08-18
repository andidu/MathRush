package com.adorastudios.mathrush.presentation.screen_main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adorastudios.mathrush.domain.DataRepository
import com.adorastudios.mathrush.domain.Record
import com.adorastudios.mathrush.domain.RecordRepository
import com.adorastudios.mathrush.domain.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    recordRepository: RecordRepository,
    private val dataRepository: DataRepository,
) : ViewModel() {
    private val _state: MutableState<MainState> = mutableStateOf(
        MainState(
            gameType = dataRepository.getLastGameType(),
        ),
    )
    val state: State<MainState> = _state

    private lateinit var record: Record
    val operations: List<String> = listOf("+", "-", "*", "/", "%", "^")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            record = recordRepository.getRecord()
            val list = populateTasks(record, null)
            withContext(Dispatchers.Main) {
                _state.value = state.value.copy(
                    tasks = list,
                )
            }
        }
    }

    private fun populateTasks(record: Record, filter: String?): List<Task> {
        return Task.tasks
            .filter { task ->
                task.operation == filter || filter == null
            }
            .map { task ->
                val (timeRecord, bestRecord) = when (task.id) {
                    RecordRepository.ADD1 -> record.add1Time to record.add1Best
                    RecordRepository.ADD2 -> record.add2Time to record.add2Best
                    RecordRepository.ADD3 -> record.add3Time to record.add3Best
                    RecordRepository.ADD4 -> record.add4Time to record.add4Best
                    RecordRepository.ADD5 -> record.add5Time to record.add5Best
                    RecordRepository.ADD6 -> record.add6Time to record.add6Best
                    RecordRepository.ADD7 -> record.add7Time to record.add7Best
                    RecordRepository.ADD8 -> record.add8Time to record.add8Best
                    RecordRepository.SUB1 -> record.sub1Time to record.sub1Best
                    RecordRepository.SUB2 -> record.sub2Time to record.sub2Best
                    RecordRepository.SUB3 -> record.sub3Time to record.sub3Best
                    RecordRepository.SUB4 -> record.sub4Time to record.sub4Best
                    RecordRepository.SUB5 -> record.sub5Time to record.sub5Best
                    RecordRepository.SUB6 -> record.sub6Time to record.sub6Best
                    RecordRepository.SUB7 -> record.sub7Time to record.sub7Best
                    RecordRepository.SUB8 -> record.sub8Time to record.sub8Best
                    RecordRepository.MUL1 -> record.mul1Time to record.mul1Best
                    RecordRepository.MUL2 -> record.mul2Time to record.mul2Best
                    RecordRepository.MUL3 -> record.mul3Time to record.mul3Best
                    RecordRepository.DIV1 -> record.div1Time to record.div1Best
                    RecordRepository.DIV2 -> record.div2Time to record.div2Best
                    RecordRepository.DIV3 -> record.div3Time to record.div3Best
                    RecordRepository.MOD1 -> record.mod1Time to record.mod1Best
                    RecordRepository.MOD2 -> record.mod2Time to record.mod2Best
                    RecordRepository.MOD3 -> record.mod3Time to record.mod3Best
                    RecordRepository.EXP1 -> record.exp1Time to record.exp1Best
                    RecordRepository.EXP2 -> record.exp2Time to record.exp2Best
                    RecordRepository.EXP3 -> record.exp3Time to record.exp3Best
                    else -> -1L to -1L
                }
                task.copy(
                    timeRecord = timeRecord,
                    bestRecord = bestRecord,
                )
            }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.GameTypeChanged -> {
                _state.value = state.value.copy(
                    gameType = event.gameType,
                )
                viewModelScope.launch {
                    dataRepository.setLastGameType(event.gameType)
                }
            }

            is MainEvent.FilterChanged -> {
                _state.value = state.value.copy(
                    selectedOperation = event.string,
                    tasks = populateTasks(record, event.string),
                )
            }
        }
    }
}
