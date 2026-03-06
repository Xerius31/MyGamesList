package com.insa.mygameslist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
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
fun GameDetailsPannel(
    game: GameComplete?, innerPadding: PaddingValues
) {
    game?.let {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(innerPadding)
        ) {
            AsyncImage(
                model = "https:${game.coverUrl}",
                placeholder = painterResource(R.drawable.cover_placeholder),
                error = painterResource(R.drawable.cover_placeholder),
                contentDescription = null,
                alignment = Alignment.TopCenter
            )
            Text(
                game.name,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                fontSize = 20.sp
            )
            Text(
                game.genreNames.joinToString(","),
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                fontStyle = FontStyle.Italic
            )

            LazyRow(
                modifier = Modifier.padding(100.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(game.plateformsNames.size) { id ->
                    Column {
                        AsyncImage(
                            model = "https:${game.plateformsLogoUrl[id]}",
                            placeholder = painterResource(R.drawable.cover_placeholder),
                            error = painterResource(R.drawable.cover_placeholder),
                            contentDescription = game.plateformsLogoUrl[id],
                            modifier = Modifier.size(60.dp),
                        )

//                        Text(
//                            platform?.name ?: "plateform not found",
//                            fontWeight = FontWeight.Thin,
//                            fontStyle = FontStyle.Italic,
//                            textAlign = TextAlign.Center
//                        )

                        // val firstReleaseDate: Long,
                    }
                }
            }
            Text(game.summary)
        }
    } ?: Text("Error 404 : No Game Found")
}


@Preview(showBackground = true)
@Composable
fun GameDetailsPreview() {

    val testGame = GameComplete(
        id = 25910,
        coverId = 18872,
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big/it58smbpvhqhbbubqsj5.jpg",
        firstReleaseDate = 1478131200,
        name = "Mallow Drops",
        summary = "Mallow Drops is a gravity puzzle where two kiwis regather their eggs in a shattered world. Help Marsh and Mallow rescue their eggs and get to the exit! Turn everything upside down as you slide, shift and move through the tricky world of Mallow Drops, a mix of platformer and a sliding block puzzle. Getting to where you need to go half the fun - just be sure to have a safe landing!\n\nWith Wooly Jumpers hopping about and Dirty Underbears surprising you suddenly, it won’t be easy, and if you’re not careful, the dreaded Dropbears may get the drop on you!\n\nCHANGE YOUR WORLD BY CHANGING YOUR PERSPECTIVE\nYou move in straight lines. Your world turns upside-down.\n\nWhen you turn the world of Mallow Drops, things shift in unexpected ways and new paths are revealed.\n\nMallow Drops is a meditative mix of platformer and a sliding block puzzle coming to Steam in 2016",
        totalRating = 96.0,

        genreIds = listOf(9, 32),
        genreNames = listOf("Puzzle", "Indie"),

        plateformIds = listOf(6, 14, 39),
        plateformsNames = listOf("Atari 8-bit", "Wii-U", "Xbox 360"),

        plateformsLogoIds = listOf(373, 151, 120),
        plateformsLogoUrl = listOf(
            "//images.igdb.com/igdb/image/upload/t_logo_med/plad.jpg",
            "//images.igdb.com/igdb/image/upload/t_logo_med/pl6n.jpg",
            "//images.igdb.com/igdb/image/upload/t_logo_med/plha.jpg"
        )
    )

    GameDetailsPannel(
        testGame, PaddingValues.Zero
    )
}