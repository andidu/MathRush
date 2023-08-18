package com.adorastudios.mathrush.data

import android.content.SharedPreferences
import com.adorastudios.mathrush.domain.Record
import com.adorastudios.mathrush.domain.RecordRepository

class RecordRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
) : RecordRepository {
    override fun getRecord(): Record {
        return Record(
            add1Time = sharedPreferences.getLong(RecordRepository.ADD1 + RecordRepository.TIME, -1),
            add2Time = sharedPreferences.getLong(RecordRepository.ADD2 + RecordRepository.TIME, -1),
            add3Time = sharedPreferences.getLong(RecordRepository.ADD3 + RecordRepository.TIME, -1),
            add4Time = sharedPreferences.getLong(RecordRepository.ADD4 + RecordRepository.TIME, -1),
            sub1Time = sharedPreferences.getLong(RecordRepository.SUB1 + RecordRepository.TIME, -1),
            sub2Time = sharedPreferences.getLong(RecordRepository.SUB2 + RecordRepository.TIME, -1),
            sub3Time = sharedPreferences.getLong(RecordRepository.SUB3 + RecordRepository.TIME, -1),
            sub4Time = sharedPreferences.getLong(RecordRepository.SUB4 + RecordRepository.TIME, -1),
            sub5Time = sharedPreferences.getLong(RecordRepository.SUB5 + RecordRepository.TIME, -1),
            sub6Time = sharedPreferences.getLong(RecordRepository.SUB6 + RecordRepository.TIME, -1),
            sub7Time = sharedPreferences.getLong(RecordRepository.SUB7 + RecordRepository.TIME, -1),
            sub8Time = sharedPreferences.getLong(RecordRepository.SUB8 + RecordRepository.TIME, -1),
            mul1Time = sharedPreferences.getLong(RecordRepository.MUL1 + RecordRepository.TIME, -1),
            mul2Time = sharedPreferences.getLong(RecordRepository.MUL2 + RecordRepository.TIME, -1),
            mul3Time = sharedPreferences.getLong(RecordRepository.MUL3 + RecordRepository.TIME, -1),
            div1Time = sharedPreferences.getLong(RecordRepository.DIV1 + RecordRepository.TIME, -1),
            div2Time = sharedPreferences.getLong(RecordRepository.DIV2 + RecordRepository.TIME, -1),
            div3Time = sharedPreferences.getLong(RecordRepository.DIV3 + RecordRepository.TIME, -1),
            add1Best = sharedPreferences.getLong(RecordRepository.ADD1 + RecordRepository.BEST, -1),
            add2Best = sharedPreferences.getLong(RecordRepository.ADD2 + RecordRepository.BEST, -1),
            add3Best = sharedPreferences.getLong(RecordRepository.ADD3 + RecordRepository.BEST, -1),
            add4Best = sharedPreferences.getLong(RecordRepository.ADD4 + RecordRepository.BEST, -1),
            sub1Best = sharedPreferences.getLong(RecordRepository.SUB1 + RecordRepository.BEST, -1),
            sub2Best = sharedPreferences.getLong(RecordRepository.SUB2 + RecordRepository.BEST, -1),
            sub3Best = sharedPreferences.getLong(RecordRepository.SUB3 + RecordRepository.BEST, -1),
            sub4Best = sharedPreferences.getLong(RecordRepository.SUB4 + RecordRepository.BEST, -1),
            sub5Best = sharedPreferences.getLong(RecordRepository.SUB5 + RecordRepository.BEST, -1),
            sub6Best = sharedPreferences.getLong(RecordRepository.SUB6 + RecordRepository.BEST, -1),
            sub7Best = sharedPreferences.getLong(RecordRepository.SUB7 + RecordRepository.BEST, -1),
            sub8Best = sharedPreferences.getLong(RecordRepository.SUB8 + RecordRepository.BEST, -1),
            mul1Best = sharedPreferences.getLong(RecordRepository.MUL1 + RecordRepository.BEST, -1),
            mul2Best = sharedPreferences.getLong(RecordRepository.MUL2 + RecordRepository.BEST, -1),
            mul3Best = sharedPreferences.getLong(RecordRepository.MUL3 + RecordRepository.BEST, -1),
            div1Best = sharedPreferences.getLong(RecordRepository.DIV1 + RecordRepository.BEST, -1),
            div2Best = sharedPreferences.getLong(RecordRepository.DIV2 + RecordRepository.BEST, -1),
            div3Best = sharedPreferences.getLong(RecordRepository.DIV3 + RecordRepository.BEST, -1),
        )
    }

    override fun getRecord(recordString: String): Long {
        return sharedPreferences.getLong(recordString, -1)
    }

    override fun setRecord(recordString: String, recordValue: Long) {
        sharedPreferences
            .edit()
            .putLong(recordString, recordValue)
            .apply()
    }
}
