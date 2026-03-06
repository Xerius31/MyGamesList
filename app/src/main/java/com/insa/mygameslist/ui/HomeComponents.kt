package com.insa.mygameslist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.insa.mygameslist.data.GameComplete

@Composable
fun LoadScreen(
    games: List<GameComplete>, innerPadding: PaddingValues, onClick: (Long) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        items(games) { game ->
            GameItem(game, onClick)
            HorizontalDivider(Modifier.fillMaxWidth(), 3.dp)
        }
    }
}

@Composable
fun GameItem(
    game: GameComplete, onClick: (Long) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clickable(onClick = { onClick(game.id) })
    ) {
        AsyncImage(
            model = "https:${game.coverUrl}",
            placeholder = painterResource(R.drawable.cover_placeholder),
            error = painterResource(R.drawable.cover_placeholder),
            contentDescription = null
        )
        Column {
            Text(
                game.name,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                fontSize = 20.sp
            )
            Text(
                "Genres : ${game.genreNames.joinToString(",")}",
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {

    val testGames = listOf(
        GameComplete(
            id = 25910,
            coverId = 18872,
            coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big/it58smbpvhqhbbubqsj5.jpg",
            firstReleaseDate = 1478131200,
            name = "Mallow Drops",
            summary = "Mallow Drops is a gravity puzzle where two kiwis regather their eggs...",
            totalRating = 96.0,

            genreIds = listOf(9, 32),
            genreNames = listOf("Puzzle", "Indie"),

            plateformIds = listOf(6, 14, 39),
            plateformsNames = listOf("PC", "Mac", "Linux"),
            plateformsLogoIds = listOf(1, 2, 3),
            plateformsLogoUrl = listOf(
                "//images.igdb.com/logo_pc.png",
                "//images.igdb.com/logo_mac.png",
                "//images.igdb.com/logo_linux.png"
            )
        ), GameComplete(
            id = 1,
            coverId = 19438,
            coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big/ul5wwtyyqzh06j98agmx.jpg",
            firstReleaseDate = 1478130022,
            name = "Youhou",
            summary = "Youhouu the game!",
            totalRating = 86.0,

            genreIds = listOf(10),
            genreNames = listOf("Racing"),

            plateformIds = listOf(14, 39),
            plateformsNames = listOf("Mac", "Linux"),
            plateformsLogoIds = listOf(2, 3),
            plateformsLogoUrl = listOf(
                "//images.igdb.com/logo_mac.png", "//images.igdb.com/logo_linux.png"
            )
        )
    )

    LoadScreen(
        testGames, PaddingValues.Zero
    ) {}
}