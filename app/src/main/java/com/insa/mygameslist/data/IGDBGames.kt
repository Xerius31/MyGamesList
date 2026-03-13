package com.insa.mygameslist.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.insa.mygameslist.R
import com.insa.mygameslist.data.restApi.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

object IGDBGames {

    var games: Map<Long, Game> = mapOf()

    fun loadFromFile(context: Context) {
        val gamesFromJson: List<Game> = Gson().fromJson(
            context.resources.openRawResource(R.raw.games).bufferedReader(),
            object : TypeToken<List<Game>>() {}.type
        )

        games = gamesFromJson.associateBy { it.id }
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

@Serializable
data class Game(
    val id: Long,
    val cover: Long? = null,
    val first_release_date: Long? = null,
    val genres: List<Long> = listOf(),
    val name: String,
    val platforms: List<Long> = listOf(),
    val summary: String? = null,
    val total_rating: Double? = null
)