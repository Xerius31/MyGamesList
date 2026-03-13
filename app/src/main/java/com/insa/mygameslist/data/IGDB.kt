package com.insa.mygameslist.data

import android.content.Context
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

object IGDB {
    var games: Map<Long, GameComplete> = mapOf()

    fun loadFromFile(context: Context) {
        IGDBGames.loadFromFile(context)
        IGDBCovers.loadFromFile(context)
        IGDBGenres.loadFromFile(context)
        IGDBPlatforms.loadFromFile(context)
        IGDBPlatformsLogo.loadFromFile(context)

        games = IGDBGames.games.map { gameEntry ->
            val plateform: List<Platform> =
                gameEntry.value.platforms.mapNotNull { IGDBPlatforms.platforms[it] }
            GameComplete(
                id = gameEntry.key,
                coverId = gameEntry.value.cover,
                coverUrl = IGDBCovers.covers[gameEntry.value.cover]?.url,
                firstReleaseDate = gameEntry.value.first_release_date,
                name = gameEntry.value.name,
                summary = gameEntry.value.summary,
                totalRating = gameEntry.value.total_rating,
                genreIds = gameEntry.value.genres,
                genreNames = gameEntry.value.genres.mapNotNull { IGDBGenres.genres[it]?.name },
                plateformIds = gameEntry.value.platforms,
                plateformsNames = plateform.map { it.name },
                plateformsLogoIds = plateform.map { it.platform_logo },
                plateformsLogoUrl = plateform.mapNotNull { IGDBPlatformsLogo.platformsLogo[it.platform_logo]?.url },
            )
        }.associateBy { it.id }
    }

    suspend fun loadFromApi(context: Context, onResult: (Map<Long, GameComplete>) -> Unit = {}) {
        coroutineScope {
            awaitAll(
                async { IGDBGames.loadFromApi(context) },
                async { IGDBCovers.loadFromApi(context) },
                async { IGDBGenres.loadFromApi(context) },
                async { IGDBPlatforms.loadFromApi(context) },
                async { IGDBPlatformsLogo.loadFromApi(context) })

            games = IGDBGames.games.map { gameEntry ->
                val plateform: List<Platform> =
                    gameEntry.value.platforms.mapNotNull { IGDBPlatforms.platforms[it] }
                GameComplete(
                    id = gameEntry.key,
                    coverId = gameEntry.value.cover,
                    coverUrl = IGDBCovers.covers[gameEntry.value.cover]?.url,
                    firstReleaseDate = gameEntry.value.first_release_date,
                    name = gameEntry.value.name,
                    summary = gameEntry.value.summary,
                    totalRating = gameEntry.value.total_rating,
                    genreIds = gameEntry.value.genres,
                    genreNames = gameEntry.value.genres.mapNotNull { IGDBGenres.genres[it]?.name },
                    plateformIds = gameEntry.value.platforms,
                    plateformsNames = plateform.map { it.name },
                    plateformsLogoIds = plateform.map { it.platform_logo },
                    plateformsLogoUrl = plateform.mapNotNull { IGDBPlatformsLogo.platformsLogo[it.platform_logo]?.url },
                )
            }.associateBy { it.id }
            onResult(games)
        }
    }
}

data class GameComplete(
    // Game
    val id: Long,
    val coverId: Long?,
    val coverUrl: String?,
    val firstReleaseDate: Long?,
    val name: String,
    val summary: String?,
    val totalRating: Double?,
    // Genre
    val genreIds: List<Long>?,
    val genreNames: List<String>,
    // Plateform
    val plateformIds: List<Long>?,
    val plateformsNames: List<String>,
    // PlateformsLogo
    val plateformsLogoIds: List<Long>,
    val plateformsLogoUrl: List<String>,

    // favori
    var isFavori: Boolean = false
)