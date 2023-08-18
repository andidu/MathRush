package com.adorastudios.mathrush.domain

import com.adorastudios.mathrush.presentation.screen_main.GameType

interface DataRepository {
    fun setLastGameType(gameType: GameType)
    fun getLastGameType(): GameType
}
