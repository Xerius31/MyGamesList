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
import com.insa.mygameslist.data.Cover
import com.insa.mygameslist.data.Game
import com.insa.mygameslist.data.Genre

@Composable
fun LoadScreen(
    games: List<Game>,
    covers: Map<Long, Cover>,
    genres: Map<Long, Genre>,
    innerPadding: PaddingValues,
    onClick: (Long) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        items(games) { game ->
            GameItem(game, covers, genres, onClick)
            HorizontalDivider(Modifier.fillMaxWidth(), 3.dp)
        }
    }
}

@Composable
fun GameItem(
    game: Game,
    covers: Map<Long, Cover>,
    genres: Map<Long, Genre>,
    onClick: (Long) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clickable(onClick = { onClick(game.id) })
    ) {
        AsyncImage(
            model = "https:${covers[game.cover]?.url}",
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
            val gameGenres =
                game.genres.joinToString(", ") { genres[it]?.name.toString() }
            Text(
                "Genres : $gameGenres",
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val testCovers = listOf(
        Cover(
            18872, "//images.igdb.com/igdb/image/upload/t_cover_big/it58smbpvhqhbbubqsj5.jpg"
        ), Cover(
            19438, "//images.igdb.com/igdb/image/upload/t_cover_big/ul5wwtyyqzh06j98agmx.jpg"
        )
    ).associateBy { it.id }

    val testGenres = listOf(
        Genre(9, "Puzzle"), Genre(10, "Racing"), Genre(32, "Indie")
    ).associateBy { it.id }

    val testGames = listOf(
        Game(
            25910,
            18872,
            1478131200,
            listOf(9, 32),
            "Mallow Drops",
            listOf(6, 14, 39),
            "Mallow Drops is a gravity puzzle where two kiwis regather their eggs in a shattered world. Help Marsh and Mallow rescue their eggs and get to the exit! Turn everything upside down as you slide, shift and move through the tricky world of Mallow Drops, a mix of platformer and a sliding block puzzle. Getting to where you need to go half the fun - just be sure to have a safe landing!\n\nWith Wooly Jumpers hopping about and Dirty Underbears surprising you suddenly, it won\u0027t be easy, and if you\u0027re not careful, the dreaded Dropbears may get the drop on you!\n\nCHANGE YOUR WORLD BY CHANGING YOUR PERSPECTIVE\nYou move in straight lines. Your world turns upside-down.\n\nWhen you turn the world of Mallow Drops, things shift in unexpected ways and new paths are revealed.\n\nMallow Drops is a meditative mix of platformer and a sliding block puzzle coming to Steam in 2016",
            96.0
        ), Game(
            1, 19438, 1478130022, listOf(10), "Youhou", listOf(14, 39), "Youhouu the game!", 86.0
        )
    )

    LoadScreen(testGames, testCovers, testGenres, PaddingValues.Zero) {}
}