package com.insa.mygameslist.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.insa.mygameslist.R

object IGDBPlatformsLogo {

    lateinit var platformsLogo: Map<Long, PlatformLogo>

    fun load(context: Context) {
        val platformsLogoFromJson: List<PlatformLogo> = Gson().fromJson(
            context.resources.openRawResource(R.raw.platform_logos).bufferedReader(),
            object : TypeToken<List<PlatformLogo>>() {}.type
        )

        platformsLogo = platformsLogoFromJson.associateBy { it.id }
    }
}

data class PlatformLogo(val id: Long, val url: String)
