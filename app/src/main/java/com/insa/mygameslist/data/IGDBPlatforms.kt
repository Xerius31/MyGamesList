package com.insa.mygameslist.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.insa.mygameslist.R

object IGDBPlatforms {

    lateinit var platforms: Map<Long, Platform>

    fun load(context: Context) {
        val platformsFromJson: List<Platform> = Gson().fromJson(
            context.resources.openRawResource(R.raw.platforms).bufferedReader(),
            object : TypeToken<List<Platform>>() {}.type
        )

        platforms = platformsFromJson.associateBy { it.id }
    }
}

data class Platform(val id: Long, val name: String, val platform_logo: Long)
