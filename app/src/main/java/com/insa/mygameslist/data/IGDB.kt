package com.insa.mygameslist.data

import android.content.Context

object IGDB {
    lateinit var games: Map<Long, GameComplete>

    fun load(context: Context) {
        IGDBGames.load(context)
        IGDBCovers.load(context)
        IGDBGenres.load(context)
        IGDBPlatforms.load(context)
        IGDBPlatformsLogo.load(context)

        games = IGDBGames.games.map { gameEntry ->
            val plateform: List<Platform> =
                gameEntry.value.platforms.mapNotNull { IGDBPlatforms.platforms[it] }
            GameComplete(
                id = gameEntry.key,
                coverId = gameEntry.value.cover,
                coverUrl = IGDBCovers.covers.getValue(gameEntry.value.cover).url,
                firstReleaseDate = gameEntry.value.firstReleaseDate,
                name = gameEntry.value.name,
                summary = gameEntry.value.summary,
                totalRating = gameEntry.value.totalRating,
                genreIds = gameEntry.value.genres,
                genreNames = gameEntry.value.genres.mapNotNull { IGDBGenres.genres[it]?.name },
                plateformIds = gameEntry.value.platforms,
                plateformsNames = plateform.map { it.name },
                plateformsLogoIds = plateform.map { it.platform_logo },
                plateformsLogoUrl = plateform.mapNotNull { IGDBPlatformsLogo.platformsLogo[it.platform_logo]?.url },
            )
        }.associateBy { it.id }
    }
}

data class GameComplete(
    // Game
    val id: Long,
    val coverId: Long,
    val coverUrl: String,
    val firstReleaseDate: Long,
    val name: String,
    val summary: String,
    val totalRating: Double,
    // Genre
    val genreIds: List<Long>,
    val genreNames: List<String>,
    // Plateform
    val plateformIds: List<Long>,
    val plateformsNames: List<String>,
    // PlateformsLogo
    val plateformsLogoIds: List<Long>,
    val plateformsLogoUrl: List<String>,

    // favori
    var isFavori: Boolean = false
)