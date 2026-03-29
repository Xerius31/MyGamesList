package com.insa.mygameslist.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.insa.mygameslist.R
import com.insa.mygameslist.domain.model.Game
import com.insa.mygameslist.ui.theme.MyGamesListTheme

@Composable
fun LoadScreen(
    games: List<Game>,
    innerPadding: PaddingValues,
    onClick: (Long) -> Unit,
    onLoadNextPage: () -> Unit
) {
    if (games.isEmpty()) {
        Text("No Match :(", modifier = Modifier.padding(innerPadding))
    } else {
        val listState = rememberLazyListState()

        // detecte quand on arrive vers la fin de la page
        val nearOfTheEnd = remember {
            derivedStateOf {
                val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                lastVisibleItem != null && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 5
            }
        }

        LaunchedEffect(nearOfTheEnd.value) {
            if (nearOfTheEnd.value) {
                onLoadNextPage()
            }
        }

        LazyColumn(
            state = listState, modifier = Modifier.padding(innerPadding)
        ) {
            items(items = games, key = { it.id }, contentType = { "game_item" }) { game ->
                GameItem(game, onClick)
                HorizontalDivider(Modifier.fillMaxWidth(), 1.dp)
            }
        }
    }
}

@Composable
fun GameItem(
    game: Game, onClick: (Long) -> Unit,
) {
    // pré-calcul des strings
    val genresText = remember(game.genreNames) { "Genres : ${game.genreNames.joinToString(", ")}" }
    val imageUrl =
        remember(game.coverUrl) { if (game.coverUrl != null) "https:${game.coverUrl}" else null }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick(game.id) }
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 16.dp)) {
        AsyncImage(
            model = imageUrl,
            placeholder = painterResource(R.drawable.cover_placeholder),
            error = painterResource(R.drawable.cover_placeholder),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = game.name,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = genresText, fontSize = 16.sp, overflow = TextOverflow.Ellipsis, maxLines = 1
            )
        }

        var isFavorite by remember { mutableStateOf(game.isFavorite) }

        Image(
            painter = painterResource(id = if (isFavorite) R.drawable.ic_star_full else R.drawable.ic_star_empty),
            contentDescription = if (isFavorite) "favorite" else "not favorite",
            modifier = Modifier
                .size(32.dp)
                .padding(4.dp)
                .clickable {
                    isFavorite = !isFavorite
                    game.isFavorite = isFavorite
                })
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val testGames = listOf(
        Game(
            id = 25910,
            coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big/it58smbpvhqhbbubqsj5.jpg",
            firstReleaseDate = 1478131200,
            name = "Mallow Drops",
            summary = "Mallow Drops is a gravity puzzle where two kiwis regather their eggs...",
            totalRating = 96.0,

            genreNames = listOf("Puzzle", "Indie"),

            platformsNames = listOf("PC", "Mac", "Linux"),
            platformsLogoUrl = listOf(
                "//images.igdb.com/logo_pc.png",
                "//images.igdb.com/logo_mac.png",
                "//images.igdb.com/logo_linux.png"
            )
        ), Game(
            id = 1,
            coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big/ul5wwtyyqzh06j98agmx.jpg",
            firstReleaseDate = 1478130022,
            name = "Youhou",
            summary = "Youhouu the game!",
            totalRating = 86.0,

            genreNames = listOf("Racing"),

            platformsNames = listOf("Mac", "Linux"),
            platformsLogoUrl = listOf(
                "//images.igdb.com/logo_mac.png", "//images.igdb.com/logo_linux.png"
            )
        )
    )

    MyGamesListTheme {
        LoadScreen(
            games = testGames,
            innerPadding = PaddingValues.Zero,
            onClick = {},
            onLoadNextPage = {})
    }
}