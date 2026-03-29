package com.insa.mygameslist.data.fetchLocal

import android.content.Context
import com.google.gson.Gson
import com.insa.mygameslist.R

object IGDBGames {

    fun loadFromFile(context: Context): Map<Long, Game> {
        val gamesFromJson: List<Game> = Gson().fromJson(
            context.resources.openRawResource(R.raw.games).bufferedReader(),
            object : com.google.gson.reflect.TypeToken<List<Game>>() {}.type
        )

        return gamesFromJson.associateBy { it.id }
    }
}

data class Game(
    val id: Long,
    val cover: Long? = null,
    val first_release_date: Long? = null,
    val genres: List<Long> = listOf(),
    val name: String,
    val platforms: List<Long> = listOf(),
    val summary: String? = null,
    val total_rating: Double? = null
)