package com.insa.mygameslist.data.mapper

import com.insa.mygameslist.data.GameComplete
import com.insa.mygameslist.domain.model.Game

fun GameComplete.toDomain(): Game {
    return Game(
        id = id,
        firstReleaseDate = firstReleaseDate,
        name = name,
        summary = summary,
        totalRating = totalRating,
        coverUrl = coverUrl,
        genreNames = genreNames,
        platformsNames = plateformsNames,
        platformsLogoUrl = plateformsLogoUrl,
        isFavorite = isFavori
    )
}
