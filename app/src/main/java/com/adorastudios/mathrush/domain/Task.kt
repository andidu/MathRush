package com.adorastudios.mathrush.domain

import com.adorastudios.mathrush.R
import kotlin.random.Random

data class Task(
    val id: String,
    val name: Int,
    val example: Int,
    val operation: String,
    val timeRecord: Long = -1,
    val bestRecord: Long = -1,
    val negativeNumbers: Boolean = false,
    val generate: () -> Pair<String, Int>,
) {
    companion object {
        val tasks = listOf(
            Task(
                id = RecordRepository.ADD1,
                name = R.string.gameNameAdd1,
                example = R.string.taskExampleAdd1,
                operation = "+",
            ) {
                val a = Random.nextInt(9) + 1
                val b = Random.nextInt(9) + 1
                "$a + $b" to (a + b)
            },
            Task(
                id = RecordRepository.ADD2,
                name = R.string.gameNameAdd2,
                example = R.string.taskExampleAdd2,
                operation = "+",
            ) {
                val a = Random.nextInt(99) + 1
                val b = Random.nextInt(99) + 1
                "$a + $b" to (a + b)
            },
            Task(
                id = RecordRepository.ADD3,
                name = R.string.gameNameAdd3,
                example = R.string.taskExampleAdd3,
                operation = "+",
            ) {
                val a = Random.nextInt(999) + 1
                val b = Random.nextInt(999) + 1
                "$a + $b" to (a + b)
            },
            Task(
                id = RecordRepository.ADD4,
                name = R.string.gameNameAdd4,
                example = R.string.taskExampleAdd4,
                operation = "+",
            ) {
                val a = Random.nextInt(9999) + 1
                val b = Random.nextInt(9999) + 1
                "$a + $b" to (a + b)
            },
            Task(
                id = RecordRepository.ADD5,
                name = R.string.gameNameAdd5,
                example = R.string.taskExampleAdd5,
                operation = "+",
                negativeNumbers = true,
            ) {
                val a = Random.nextInt(19) - 9
                val b = Random.nextInt(19) - 9
                val bStr = if (b < 0) "($b)" else "$b"
                "$a + $bStr" to (a + b)
            },
            Task(
                id = RecordRepository.ADD6,
                name = R.string.gameNameAdd6,
                example = R.string.taskExampleAdd6,
                operation = "+",
                negativeNumbers = true,
            ) {
                val a = Random.nextInt(199) - 99
                val b = Random.nextInt(199) - 99
                val bStr = if (b < 0) "($b)" else "$b"
                "$a + $bStr" to (a + b)
            },
            Task(
                id = RecordRepository.ADD7,
                name = R.string.gameNameAdd7,
                example = R.string.taskExampleAdd7,
                operation = "+",
                negativeNumbers = true,
            ) {
                val a = Random.nextInt(1999) - 999
                val b = Random.nextInt(1999) - 999
                val bStr = if (b < 0) "($b)" else "$b"
                "$a + $bStr" to (a + b)
            },
            Task(
                id = RecordRepository.ADD8,
                name = R.string.gameNameAdd8,
                example = R.string.taskExampleAdd8,
                operation = "+",
                negativeNumbers = true,
            ) {
                val a = Random.nextInt(19999) - 9999
                val b = Random.nextInt(19999) - 9999
                val bStr = if (b < 0) "($b)" else "$b"
                "$a + $bStr" to (a + b)
            },
            Task(
                id = RecordRepository.SUB1,
                name = R.string.gameNameSub1,
                example = R.string.taskExampleSub1,
                operation = "-",
            ) {
                val a = Random.nextInt(9) + 1
                val b = Random.nextInt(9) + 1
                if (a > b) {
                    "$a - $b" to (a - b)
                } else {
                    "$b - $a" to (b - a)
                }
            },
            Task(
                id = RecordRepository.SUB2,
                name = R.string.gameNameSub2,
                example = R.string.taskExampleSub2,
                operation = "-",
            ) {
                val a = Random.nextInt(99) + 1
                val b = Random.nextInt(99) + 1
                if (a > b) {
                    "$a - $b" to (a - b)
                } else {
                    "$b - $a" to (b - a)
                }
            },
            Task(
                id = RecordRepository.SUB3,
                name = R.string.gameNameSub3,
                example = R.string.taskExampleSub3,
                operation = "-",
            ) {
                val a = Random.nextInt(999) + 1
                val b = Random.nextInt(999) + 1
                if (a > b) {
                    "$a - $b" to (a - b)
                } else {
                    "$b - $a" to (b - a)
                }
            },
            Task(
                id = RecordRepository.SUB4,
                name = R.string.gameNameSub4,
                example = R.string.taskExampleSub4,
                operation = "-",
            ) {
                val a = Random.nextInt(9999) + 1
                val b = Random.nextInt(9999) + 1
                if (a > b) {
                    "$a - $b" to (a - b)
                } else {
                    "$b - $a" to (b - a)
                }
            },
            Task(
                id = RecordRepository.SUB5,
                name = R.string.gameNameSub5,
                example = R.string.taskExampleSub5,
                operation = "-",
                negativeNumbers = true,
            ) {
                val a = Random.nextInt(10)
                val b = Random.nextInt(10)
                "$a - $b" to (a - b)
            },
            Task(
                id = RecordRepository.SUB6,
                name = R.string.gameNameSub6,
                example = R.string.taskExampleSub6,
                operation = "-",
                negativeNumbers = true,
            ) {
                val a = Random.nextInt(100)
                val b = Random.nextInt(100)
                "$a - $b" to (a - b)
            },
            Task(
                id = RecordRepository.SUB7,
                name = R.string.gameNameSub7,
                example = R.string.taskExampleSub7,
                operation = "-",
                negativeNumbers = true,
            ) {
                val a = Random.nextInt(1000)
                val b = Random.nextInt(1000)
                "$a - $b" to (a - b)
            },
            Task(
                id = RecordRepository.SUB8,
                name = R.string.gameNameSub8,
                example = R.string.taskExampleSub8,
                operation = "-",
                negativeNumbers = true,
            ) {
                val a = Random.nextInt(10000)
                val b = Random.nextInt(10000)
                "$a - $b" to (a - b)
            },
            Task(
                id = RecordRepository.MUL1,
                name = R.string.gameNameMul1,
                example = R.string.taskExampleMul1,
                operation = "*",
            ) {
                val a = Random.nextInt(9) + 1
                val b = Random.nextInt(9) + 1
                "$a * $b" to (a * b)
            },
            Task(
                id = RecordRepository.MUL2,
                name = R.string.gameNameMul2,
                example = R.string.taskExampleMul2,
                operation = "*",
            ) {
                val a = Random.nextInt(99) + 1
                val b = Random.nextInt(9) + 1
                if (Random.nextBoolean()) {
                    "$a * $b" to (a * b)
                } else {
                    "$b * $a" to (a * b)
                }
            },
            Task(
                id = RecordRepository.MUL3,
                name = R.string.gameNameMul3,
                example = R.string.taskExampleMul3,
                operation = "*",
            ) {
                val a = Random.nextInt(99) + 1
                val b = Random.nextInt(99) + 1
                "$a * $b" to (a * b)
            },
            Task(
                id = RecordRepository.DIV1,
                name = R.string.gameNameDiv1,
                example = R.string.taskExampleDiv1,
                operation = "/",
            ) {
                val a = Random.nextInt(9) + 1
                val b = Random.nextInt(9) + 1
                "${a * b} / $b" to (a)
            },
            Task(
                id = RecordRepository.DIV2,
                name = R.string.gameNameDiv2,
                example = R.string.taskExampleDiv2,
                operation = "/",
            ) {
                val a = Random.nextInt(99) + 1
                val b = Random.nextInt(9) + 1
                if (Random.nextBoolean()) {
                    "${a * b} / $b" to a
                } else {
                    "${a * b} / $a" to b
                }
            },
            Task(
                id = RecordRepository.DIV3,
                name = R.string.gameNameDiv3,
                example = R.string.taskExampleDiv3,
                operation = "/",
            ) {
                val a = Random.nextInt(99) + 1
                val b = Random.nextInt(99) + 1
                "${a * b} / $b" to (a)
            },
            Task(
                id = RecordRepository.MOD1,
                name = R.string.gameNameMod1,
                example = R.string.taskExampleMod1,
                operation = "%",
            ) {
                val a = Random.nextInt(9) + 1
                val b = Random.nextInt(9) + 1
                if (a > b) {
                    "$a % $b" to (a % b)
                } else {
                    "$b % $a" to (b % a)
                }
            },
            Task(
                id = RecordRepository.MOD2,
                name = R.string.gameNameMod2,
                example = R.string.taskExampleMod2,
                operation = "%",
            ) {
                val a = Random.nextInt(99) + 1
                val b = Random.nextInt(9) + 1
                if (a > b) {
                    "$a % $b" to (a % b)
                } else {
                    "$b % $a" to (b % a)
                }
            },
            Task(
                id = RecordRepository.MOD3,
                name = R.string.gameNameMod3,
                example = R.string.taskExampleMod3,
                operation = "%",
            ) {
                val a = Random.nextInt(999) + 1
                val b = Random.nextInt(9) + 1
                if (a > b) {
                    "$a % $b" to (a % b)
                } else {
                    "$b % $a" to (b % a)
                }
            },
            Task(
                id = RecordRepository.EXP1,
                name = R.string.gameNameExp1,
                example = R.string.taskExampleExp1,
                operation = "^",
            ) {
                val a = Random.nextInt(9) + 1
                "$a ^ 2" to (a * a)
            },
            Task(
                id = RecordRepository.EXP2,
                name = R.string.gameNameExp2,
                example = R.string.taskExampleExp2,
                operation = "^",
            ) {
                val a = Random.nextInt(99) + 1
                "$a ^ 2" to (a * a)
            },
            Task(
                id = RecordRepository.EXP3,
                name = R.string.gameNameExp3,
                example = R.string.taskExampleExp3,
                operation = "^",
            ) {
                val a = Random.nextInt(99) + 1
                val b = if (Random.nextBoolean()) 2 else 3
                "$a ^ $b" to (if (b == 3) a * a * a else a * a)
            },
        )
    }
}
