package com.insa.mygameslist.domain.usecase

import com.insa.mygameslist.domain.model.Game
import com.insa.mygameslist.domain.repository.GameRepository

class SearchGamesUseCase(private val repository: GameRepository) {
    suspend operator fun invoke(query: String): List<Game> {
        return repository.searchGames(query)
    }
}
