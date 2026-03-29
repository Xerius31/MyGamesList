package com.insa.mygameslist.ui.screens

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.insa.mygameslist.R
import com.insa.mygameslist.domain.model.Game
import com.insa.mygameslist.ui.components.GameDetailsPannel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailsScreen(
    game: Game?, onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                containerColor = Color.Magenta, titleContentColor = Color.Black
            ), title = {
                Text(game?.name ?: "Game Not Found")
            }, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                        contentDescription = "Back"
                    )
                }
            })
        }, contentWindowInsets = WindowInsets.systemBars, modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        GameDetailsPannel(
            game = game, innerPadding = innerPadding
        )
    }
}
