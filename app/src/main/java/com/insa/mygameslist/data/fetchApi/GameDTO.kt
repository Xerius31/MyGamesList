package com.insa.mygameslist.data.fetchApi

import kotlinx.serialization.Serializable

@Serializable
data class GameDTO(
    // Game
    val id: Long,
    val first_release_date: Long? = null,
    val name: String,
    val summary: String? = null,
    val total_rating: Double? = null,
    val cover: CoverDTO? = null,
    val genres: List<GenreDTO>? = listOf(),
    val platforms: List<PlatformDTO>? = listOf()
)

@Serializable
data class GenreDTO(
    val id: Long, val name: String
)

@Serializable
data class CoverDTO(
    val id: Long, val url: String?
)

@Serializable
data class PlatformDTO(
    val id: Long, val name: String, val platform_logo: PlatformLogoDTO? = null
)

@Serializable
data class PlatformLogoDTO(
    val id: Long, val url: String
)


