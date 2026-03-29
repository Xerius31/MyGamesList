package com.insa.mygameslist.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.insa.mygameslist.domain.model.Game
import com.insa.mygameslist.ui.components.LoadScreen
import com.insa.mygameslist.ui.components.SimpleSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    games: List<Game>,
    searchTextState: TextFieldState,
    onGameClick: (Long) -> Unit,
    onLoadNextPage: () -> Unit
) {
    Scaffold(
        topBar = {
            Row {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = Color.Magenta, titleContentColor = Color.Black
                    ), title = {
                        SimpleSearchBar(
                            textFieldState = searchTextState,
                            onSearch = {},
                            searchResults = games.take(50).map { it.name },
                        )
                    })
            }
        }, contentWindowInsets = WindowInsets.systemBars, modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LoadScreen(
            games = games,
            innerPadding = innerPadding,
            onClick = onGameClick,
            onLoadNextPage = onLoadNextPage
        )
    }
}
