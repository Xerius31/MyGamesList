package com.insa.mygameslist.domain.repository

import com.insa.mygameslist.domain.model.Game

interface GameRepository {
    suspend fun getGames(page: Int): List<Game>
    suspend fun searchGames(query: String): List<Game>
}
