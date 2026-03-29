package com.insa.mygameslist.data

import android.content.Context

interface DataSource {
    suspend fun fetch(context: Context, page: Int): List<GameComplete>
    suspend fun search(context: Context, query: String): List<GameComplete>
}
