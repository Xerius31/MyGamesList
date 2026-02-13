package com.insa.mygameslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.insa.mygameslist.data.IGDBCovers
import com.insa.mygameslist.data.IGDBGames
import com.insa.mygameslist.data.IGDBGenres
import com.insa.mygameslist.data.IGDBPlatforms
import com.insa.mygameslist.data.IGDBPlatformsLogo
import com.insa.mygameslist.ui.GameDetails
import com.insa.mygameslist.ui.GameDetailsPannel
import com.insa.mygameslist.ui.Home
import com.insa.mygameslist.ui.LoadScreen
import com.insa.mygameslist.ui.theme.MyGamesListTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        IGDBGames.load(this)
        IGDBCovers.load(this)
        IGDBGenres.load(this)
        IGDBPlatforms.load(this)
        IGDBPlatformsLogo.load(this)

        enableEdgeToEdge()

        setContent {
            val backStack = remember { mutableStateListOf<Any>(Home) }

            MyGamesListTheme {
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = entryProvider {

                        // HOME SCREEN
                        entry<Home> {
                            Scaffold(
                                topBar = {
                                    TopAppBar(
                                        colors = topAppBarColors(
                                            containerColor = Color.Magenta,
                                            titleContentColor = Color.Black
                                        ), title = { Text("My Games List") })
                                },
                                contentWindowInsets = WindowInsets.systemBars,
                                modifier = Modifier.fillMaxSize()
                            ) { innerPadding ->
                                LoadScreen(
                                    games = IGDBGames.games.values.toList(),
                                    covers = IGDBCovers.covers,
                                    genres = IGDBGenres.genres,
                                    innerPadding = innerPadding,
                                    onClick = { gameId ->
                                        backStack.add(GameDetails(gameId))
                                    })
                            }
                        }

                        // GAME DETAILS SCREEN
                        entry<GameDetails> { key ->
                            val game = IGDBGames.games[key.gameId]

                            Scaffold(
                                topBar = {
                                    TopAppBar(
                                        colors = topAppBarColors(
                                        containerColor = Color.Magenta,
                                        titleContentColor = Color.Black
                                    ), title = {
                                        Text(game?.name ?: "Game Not Found")
                                    }, navigationIcon = {
                                        IconButton(
                                            onClick = { backStack.removeLastOrNull() }) {
                                            Icon(
                                                painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                                                contentDescription = "Back"
                                            )
                                        }
                                    })
                                },
                                contentWindowInsets = WindowInsets.systemBars,
                                modifier = Modifier.fillMaxSize()
                            ) { innerPadding ->
                                GameDetailsPannel(
                                    game = game,
                                    covers = IGDBCovers.covers,
                                    genres = IGDBGenres.genres,
                                    platforms = IGDBPlatforms.platforms,
                                    platformsLogo = IGDBPlatformsLogo.platformsLogo,
                                    innerPadding = innerPadding
                                )
                            }
                        }
                    })
            }
        }
    }
}
