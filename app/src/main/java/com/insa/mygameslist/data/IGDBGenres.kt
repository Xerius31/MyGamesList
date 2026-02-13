package com.insa.mygameslist.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.insa.mygameslist.R

object IGDBGenres {

    lateinit var genres: Map<Long, Genre>

    fun load(context: Context) {
        val genresFromJson: List<Genre> = Gson().fromJson(
            context.resources.openRawResource(R.raw.genres).bufferedReader(),
            object : TypeToken<List<Genre>>() {}.type
        )

        genres = genresFromJson.associateBy { it.id }
    }
}

data class Genre(val id: Long, val name: String)