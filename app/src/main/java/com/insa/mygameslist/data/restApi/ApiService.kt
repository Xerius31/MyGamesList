package com.insa.mygameslist.data.restApi

import com.insa.mygameslist.data.Game
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers(
        "Client-ID: [client id]",
        "Authorization: Bearer [auth token]",
        "Content-Type: application/json",
    )
    @POST("games")
    suspend fun getGames(@Body query: RequestBody): Response<List<Game>>
}
