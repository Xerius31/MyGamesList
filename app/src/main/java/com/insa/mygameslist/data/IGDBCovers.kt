package com.insa.mygameslist.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.insa.mygameslist.R

object IGDBCovers {

    lateinit var covers: Map<Long, Cover>

    fun load(context: Context) {
        val coversFromJson: List<Cover> = Gson().fromJson(
            context.resources.openRawResource(R.raw.covers).bufferedReader(),
            object : TypeToken<List<Cover>>() {}.type
        )

        covers = coversFromJson.associateBy { it.id }
    }
}

data class Cover(val id: Long, val url: String)