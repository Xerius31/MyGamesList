package com.insa.mygameslist.data.fetchLocal

import android.content.Context
import com.google.gson.Gson
import com.insa.mygameslist.R

object IGDBPlatforms {

    fun loadFromFile(context: Context): Map<Long, Platform> {
        val platformsFromJson: List<Platform> = Gson().fromJson(
            context.resources.openRawResource(R.raw.platforms).bufferedReader(),
            object : com.google.gson.reflect.TypeToken<List<Platform>>() {}.type
        )

        return platformsFromJson.associateBy { it.id }
    }

}

data class Platform(val id: Long, val name: String, val platform_logo: Long)
