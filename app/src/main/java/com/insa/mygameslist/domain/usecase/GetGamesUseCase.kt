package com.insa.mygameslist.domain.usecase

import com.insa.mygameslist.domain.model.Game
import com.insa.mygameslist.domain.repository.GameRepository

class GetGamesUseCase(private val repository: GameRepository) {
    suspend operator fun invoke(page: Int): List<Game> {
        return repository.getGames(page)
    }
}
