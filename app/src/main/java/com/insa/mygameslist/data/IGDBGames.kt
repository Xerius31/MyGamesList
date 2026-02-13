package com.insa.mygameslist.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.insa.mygameslist.R

object IGDBGames {

    lateinit var games: Map<Long, Game>

    fun load(context: Context) {
        val gamesFromJson: List<Game> = Gson().fromJson(
            context.resources.openRawResource(R.raw.games).bufferedReader(),
            object : TypeToken<List<Game>>() {}.type
        )

        games = gamesFromJson.associateBy { it.id }
    }
}

data class Game(
    val id: Long,
    val cover: Long,
    val firstReleaseDate: Long,
    val genres: List<Long>,
    val name: String,
    val platforms: List<Long>,
    val summary: String,
    val totalRating: Double
)


