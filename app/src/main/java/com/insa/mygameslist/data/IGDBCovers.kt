package com.insa.mygameslist.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.insa.mygameslist.R
import com.insa.mygameslist.data.IGDBGames.games
import com.insa.mygameslist.data.restApi.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

object IGDBCovers {

    lateinit var covers: Map<Long, Cover>

    fun loadFromFile(context: Context) {
        val coversFromJson: List<Cover> = Gson().fromJson(
            context.resources.openRawResource(R.raw.covers).bufferedReader(),
            object : TypeToken<List<Cover>>() {}.type
        )

        covers = coversFromJson.associateBy { it.id }
    }

    suspend fun loadFromApi(context: Context) {
        val query = "fields *;"
        val response = ApiClient.apiService.getGames(
            query.toRequestBody("text/plain".toMediaType())
        )

        if (response.isSuccessful && response.body() != null) {
            val gameList = response.body()
            Log.d("mygamelist", "end fetch games")
            games = gameList?.associateBy { it.id } ?: mapOf()
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context, "Error Occurred: ${response.message()}", Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

data class Cover(val id: Long, val url: String)