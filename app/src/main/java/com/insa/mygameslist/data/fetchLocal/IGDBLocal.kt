package com.insa.mygameslist.data.fetchLocal

import android.content.Context
import com.insa.mygameslist.data.DataSource
import com.insa.mygameslist.data.GameComplete

class IGDBLocal : DataSource {

    private suspend fun getAllGames(context: Context): List<GameComplete> {
        val games = IGDBGames.loadFromFile(context)
        val covers = IGDBCovers.loadFromFile(context)
        val genres = IGDBGenres.loadFromFile(context)
        val platforms = IGDBPlatforms.loadFromFile(context)
        val platformsLogo = IGDBPlatformsLogo.loadFromFile(context)

        return games.map { gameEntry ->
            val platformGame: List<Platform> =
                gameEntry.value.platforms.mapNotNull { platforms[it] }
            GameComplete(
                id = gameEntry.key,
                coverUrl = covers[gameEntry.value.cover]?.url,
                firstReleaseDate = gameEntry.value.first_release_date,
                name = gameEntry.value.name,
                summary = gameEntry.value.summary,
                totalRating = gameEntry.value.total_rating,
                genreNames = gameEntry.value.genres.mapNotNull { genres[it]?.name },
                plateformsNames = platformGame.map { it.name },
                plateformsLogoUrl = platformGame.mapNotNull { platformsLogo[it.platform_logo]?.url },
            )
        }
    }

    override suspend fun fetch(context: Context, page: Int): List<GameComplete> {
        return getAllGames(context)
    }

    override suspend fun search(context: Context, query: String): List<GameComplete> {
        val allGames = getAllGames(context)
        return allGames.filter { game ->
            game.name.contains(query, ignoreCase = true) ||
            game.genreNames.any { it.contains(query, ignoreCase = true) } ||
            game.plateformsNames.any { it.contains(query, ignoreCase = true) }
        }
    }
}
