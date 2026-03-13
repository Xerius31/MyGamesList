package com.insa.mygameslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.insa.mygameslist.data.GameComplete
import com.insa.mygameslist.data.IGDB
import com.insa.mygameslist.ui.GameDetails
import com.insa.mygameslist.ui.GameDetailsPannel
import com.insa.mygameslist.ui.Home
import com.insa.mygameslist.ui.LoadScreen
import com.insa.mygameslist.ui.SimpleSearchBar
import com.insa.mygameslist.ui.theme.MyGamesListTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val backStack = remember { mutableStateListOf<Any>(Home) }

            // IGDB.load(this)

            val (gameList, setGameList) = remember { mutableStateOf<Map<Long, GameComplete>>(mapOf()) }

            LaunchedEffect(Unit) {
                IGDB.loadFromApi(this@MainActivity) { games ->
                    Log.d("mygamelistValues", games.toString())
                    setGameList(games)
                }
            }

            val searchText = rememberTextFieldState()
            val searchResults = gameList.values.filter { game ->
                game.name.contains(
                    searchText.text.toString(), ignoreCase = true
                ) || game.genreNames.any {
                    it.contains(
                        searchText.text.toString(), ignoreCase = true
                    )
                } || game.plateformsNames.any {
                    it.contains(searchText.text.toString(), ignoreCase = true)
                }
            }

            MyGamesListTheme {
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = entryProvider {

                        // HOME SCREEN
                        entry<Home> {
                            Scaffold(
                                topBar = {
                                    Row {
                                        TopAppBar(
                                            colors = topAppBarColors(
                                                containerColor = Color.Magenta,
                                                titleContentColor = Color.Black
                                            ), title = {
                                                SimpleSearchBar(
                                                    textFieldState = searchText,
                                                    onSearch = {},
                                                    searchResults = searchResults.map { it.name },
                                                )
                                            })
                                    }
                                },
                                contentWindowInsets = WindowInsets.systemBars,
                                modifier = Modifier.fillMaxSize()
                            ) { innerPadding ->
                                LoadScreen(
                                    games = searchResults,
                                    innerPadding = innerPadding,
                                    onClick = { gameId ->
                                        backStack.add(GameDetails(gameId))
                                    })
                            }
                        }

                        // GAME DETAILS SCREEN
                        entry<GameDetails> { key ->
                            val game = gameList[key.gameId]

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
                                    game = game, innerPadding = innerPadding
                                )
                            }
                        }
                    })
            }
        }
    }
}
