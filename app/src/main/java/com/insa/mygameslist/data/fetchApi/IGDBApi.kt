package com.insa.mygameslist.data.fetchApi

import android.content.Context
import com.insa.mygameslist.data.DataSource
import com.insa.mygameslist.data.GameComplete
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class IGDBApi : DataSource {

    private val pageSize = 100

    private fun mapToGameComplete(gameList: List<GameDTO>?): List<GameComplete> {
        return gameList?.map { gameDTO ->
            GameComplete(
                id = gameDTO.id,
                firstReleaseDate = gameDTO.first_release_date,
                name = gameDTO.name,
                summary = gameDTO.summary,
                totalRating = gameDTO.total_rating,
                coverUrl = gameDTO.cover?.url,
                genreNames = gameDTO.genres?.map { it.name } ?: listOf(),
                plateformsNames = gameDTO.platforms?.map { it.name } ?: listOf(),
                plateformsLogoUrl = gameDTO.platforms?.mapNotNull { it.platform_logo?.url }
                    ?: listOf(),
            )
        } ?: listOf()
    }

    override suspend fun fetch(context: Context, page: Int): List<GameComplete> {
        val query =
            "fields id, first_release_date, name, summary, total_rating, genres.name, cover.url, platforms.name, platforms.platform_logo.url; limit $pageSize; offset ${pageSize * page};"
        val response: Response<List<GameDTO>> = ApiClient.apiService.getGames(
            query.toRequestBody("text/plain".toMediaType())
        )

        if (response.isSuccessful) {
            return mapToGameComplete(response.body())
        } else {
            throw Exception("error while fetching the api: ${response.message()}")
        }
    }

    override suspend fun search(context: Context, query: String): List<GameComplete> {
        val query =
            "fields id, first_release_date, name, summary, total_rating, genres.name, cover.url, platforms.name, platforms.platform_logo.url; where name ~ *\"$query\"* | genres.name ~ *\"$query\"* | platforms.name ~ *\"$query\"*; limit 50;"
        val response: Response<List<GameDTO>> = ApiClient.apiService.getGames(
            query.toRequestBody("text/plain".toMediaType())
        )

        if (response.isSuccessful) {
            return mapToGameComplete(response.body())
        } else {
            throw Exception("error while searching the api: ${response.message()}")
        }
    }
}
