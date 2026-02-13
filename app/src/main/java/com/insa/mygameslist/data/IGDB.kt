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
    }
}

data class GameComplete(
    val game: Game,
    val cover: Cover,
    val genres: List<Genre>,
    val plateforms: List<Platform>,
    val plateformsLogo: List<PlatformLogo>
)