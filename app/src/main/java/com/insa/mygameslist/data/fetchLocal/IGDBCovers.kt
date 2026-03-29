package com.insa.mygameslist.data.fetchLocal

import android.content.Context
import com.google.gson.Gson
import com.insa.mygameslist.R

object IGDBCovers {

    fun loadFromFile(context: Context): Map<Long, Cover> {
        val coversFromJson: List<Cover> = Gson().fromJson(
            context.resources.openRawResource(R.raw.covers).bufferedReader(),
            object : com.google.gson.reflect.TypeToken<List<Cover>>() {}.type
        )

        return coversFromJson.associateBy { it.id }
    }
}

data class Cover(val id: Long, val url: String)
