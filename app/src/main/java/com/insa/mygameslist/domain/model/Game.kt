package com.insa.mygameslist.domain.model

data class Game(
    val id: Long,
    val firstReleaseDate: Long?,
    val name: String,
    val summary: String?,
    val totalRating: Double?,
    val coverUrl: String?,
    val genreNames: List<String>,
    val platformsNames: List<String>,
    val platformsLogoUrl: List<String>,
    var isFavorite: Boolean = false
)
