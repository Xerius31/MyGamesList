package com.insa.mygameslist.data.repository

import android.content.Context
import com.insa.mygameslist.data.DataSource
import com.insa.mygameslist.data.mapper.toDomain
import com.insa.mygameslist.domain.model.Game
import com.insa.mygameslist.domain.repository.GameRepository

class GameRepositoryImpl(
    private val context: Context, private val dataSource: DataSource
) : GameRepository {
    override suspend fun getGames(page: Int): List<Game> {
        return dataSource.fetch(context, page).map { it.toDomain() }
    }

    override suspend fun searchGames(query: String): List<Game> {
        return dataSource.search(context, query).map { it.toDomain() }
    }
}
