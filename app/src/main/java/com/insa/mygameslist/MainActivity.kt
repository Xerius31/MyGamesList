package com.insa.mygameslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.insa.mygameslist.data.fetchApi.IGDBApi
import com.insa.mygameslist.data.repository.GameRepositoryImpl
import com.insa.mygameslist.domain.usecase.GetGamesUseCase
import com.insa.mygameslist.domain.usecase.SearchGamesUseCase
import com.insa.mygameslist.ui.GameDetails
import com.insa.mygameslist.ui.Home
import com.insa.mygameslist.ui.screens.GameDetailsScreen
import com.insa.mygameslist.ui.screens.HomeScreen
import com.insa.mygameslist.ui.theme.MyGamesListTheme
import com.insa.mygameslist.ui.viewmodel.MainViewModel
import com.insa.mygameslist.ui.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        val repository = GameRepositoryImpl(applicationContext, IGDBApi())
        MainViewModelFactory(
            GetGamesUseCase(repository), SearchGamesUseCase(repository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val backStack = remember { mutableStateListOf<Any>(Home) }
            val games by viewModel.games.collectAsState()
            val filteredGames by viewModel.filteredGames.collectAsState()
            val error by viewModel.errorMessage.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.loadGames()
            }

            MyGamesListTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column {
                        error?.let {
                            Text(
                                text = it,
                                color = Color.White,
                                modifier = Modifier
                                    .background(Color.Red)
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                        NavDisplay(
                            modifier = Modifier.weight(1f),
                            backStack = backStack,
                            onBack = { backStack.removeLastOrNull() },
                            entryProvider = entryProvider {
                                entry<Home> {
                                    HomeScreen(
                                        games = filteredGames,
                                        searchTextState = viewModel.searchText,
                                        onGameClick = { gameId -> backStack.add(GameDetails(gameId)) },
                                        onLoadNextPage = { viewModel.loadNextPage() })
                                }
                                entry<GameDetails> { key ->
                                    GameDetailsScreen(
                                        game = games[key.gameId],
                                        onBack = { backStack.removeLastOrNull() })
                                }
                            })
                    }

                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
