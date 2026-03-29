package com.insa.mygameslist.data.fetchLocal

import android.content.Context
import com.google.gson.Gson
import com.insa.mygameslist.R

object IGDBGenres {

    fun loadFromFile(context: Context): Map<Long, Genre> {
        val genresFromJson: List<Genre> = Gson().fromJson(
            context.resources.openRawResource(R.raw.genres).bufferedReader(),
            object : com.google.gson.reflect.TypeToken<List<Genre>>() {}.type
        )

        return genresFromJson.associateBy { it.id }
    }
}

data class Genre(val id: Long, val name: String)