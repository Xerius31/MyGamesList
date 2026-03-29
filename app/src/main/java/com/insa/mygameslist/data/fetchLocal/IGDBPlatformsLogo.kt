package com.insa.mygameslist.data.fetchLocal

import android.content.Context
import com.google.gson.Gson
import com.insa.mygameslist.R

object IGDBPlatformsLogo {

    fun loadFromFile(context: Context): Map<Long, PlatformLogo> {
        val platformsLogoFromJson: List<PlatformLogo> = Gson().fromJson(
            context.resources.openRawResource(R.raw.platform_logos).bufferedReader(),
            object : com.google.gson.reflect.TypeToken<List<PlatformLogo>>() {}.type
        )

        return platformsLogoFromJson.associateBy { it.id }
    }
}

data class PlatformLogo(val id: Long, val url: String)
