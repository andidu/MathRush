package com.adorastudios.mathrush.domain

interface RecordRepository {
    fun getRecord(): Record
    fun getRecord(recordString: String): Long
    fun setRecord(recordString: String, recordValue: Long)

    companion object Time {
        const val ADD1 = "add1_"
        const val ADD2 = "add2_"
        const val ADD3 = "add3_"
        const val ADD4 = "add4_"
        const val ADD5 = "add5_"
        const val ADD6 = "add6_"
        const val ADD7 = "add7_"
        const val ADD8 = "add8_"
        const val SUB1 = "sub1_"
        const val SUB2 = "sub2_"
        const val SUB3 = "sub3_"
        const val SUB4 = "sub4_"
        const val SUB5 = "sub5_"
        const val SUB6 = "sub6_"
        const val SUB7 = "sub7_"
        const val SUB8 = "sub8_"
        const val MUL1 = "mul1_"
        const val MUL2 = "mul2_"
        const val MUL3 = "mul3_"
        const val DIV1 = "div1_"
        const val DIV2 = "div2_"
        const val DIV3 = "div3_"
        const val MOD1 = "mod1_"
        const val MOD2 = "mod2_"
        const val MOD3 = "mod3_"
        const val EXP1 = "exp1_"
        const val EXP2 = "exp2_"
        const val EXP3 = "exp3_"

        const val TIME = "time"
        const val BEST = "best"
    }
}
