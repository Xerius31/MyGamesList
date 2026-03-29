package com.insa.mygameslist.data

data class GameComplete(
    // Game
    val id: Long,
    val firstReleaseDate: Long?,
    val name: String,
    val summary: String?,
    val totalRating: Double?,
    // Cover
    val coverUrl: String?,
    // Genre
    val genreNames: List<String>,
    // Plateform
    val plateformsNames: List<String>,
    // PlateformsLogo
    val plateformsLogoUrl: List<String>,

    // favori
    var isFavori: Boolean = false
)