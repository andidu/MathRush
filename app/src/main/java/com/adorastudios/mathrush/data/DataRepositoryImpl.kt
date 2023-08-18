package com.adorastudios.mathrush.data

import android.content.SharedPreferences
import com.adorastudios.mathrush.domain.DataRepository
import com.adorastudios.mathrush.presentation.screen_main.GameType

class DataRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
) : DataRepository {

    companion object {
        private const val LAST_GAME_STRING = "last_game"
    }

    override fun setLastGameType(gameType: GameType) {
        sharedPreferences
            .edit()
            .putInt(
                LAST_GAME_STRING,
                gameType.let {
                    when (it) {
                        GameType.Best -> 0
                        GameType.Time -> -1
                    }
                },
            )
            .apply()
    }

    override fun getLastGameType(): GameType {
        return sharedPreferences.getInt(LAST_GAME_STRING, 0).let {
            when (it) {
                0 -> GameType.Best
                else -> GameType.Time
            }
        }
    }
}
