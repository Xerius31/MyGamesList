package com.insa.mygameslist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.insa.mygameslist.data.Cover
import com.insa.mygameslist.data.Game
import com.insa.mygameslist.data.Genre
import com.insa.mygameslist.data.Platform
import com.insa.mygameslist.data.PlatformLogo

@Composable
fun GameDetailsPannel(
    game: Game?,
    covers: Map<Long, Cover>,
    genres: Map<Long, Genre>,
    platforms: Map<Long, Platform>,
    platformsLogo: Map<Long, PlatformLogo>,
    innerPadding: PaddingValues
) {
    game?.let {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(innerPadding)
        ) {
            AsyncImage(
                model = "https:${covers[game.cover]?.url}",
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
            val gameGenres = game.genres.joinToString(", ") { genres[it]?.name.toString() }
            Text(
                gameGenres,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                fontStyle = FontStyle.Italic
            )

            LazyRow(modifier = Modifier.padding(100.dp, 10.dp), verticalAlignment = Alignment.CenterVertically) {
                items(game.platforms) { platformGame ->
                    Column {
                        val platform = platforms[platformGame]
                        AsyncImage(
                            model = "https:${platformsLogo[platform?.platform_logo]?.url}",
                            placeholder = painterResource(R.drawable.cover_placeholder),
                            error = painterResource(R.drawable.cover_placeholder),
                            contentDescription = platform?.name,
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

    val testGame = Game(
        25910,
        18872,
        1478131200,
        listOf(9, 32),
        "Mallow Drops",
        listOf(6, 14, 39),
        "Mallow Drops is a gravity puzzle where two kiwis regather their eggs in a shattered world. Help Marsh and Mallow rescue their eggs and get to the exit! Turn everything upside down as you slide, shift and move through the tricky world of Mallow Drops, a mix of platformer and a sliding block puzzle. Getting to where you need to go half the fun - just be sure to have a safe landing!\n\nWith Wooly Jumpers hopping about and Dirty Underbears surprising you suddenly, it won\u0027t be easy, and if you\u0027re not careful, the dreaded Dropbears may get the drop on you!\n\nCHANGE YOUR WORLD BY CHANGING YOUR PERSPECTIVE\nYou move in straight lines. Your world turns upside-down.\n\nWhen you turn the world of Mallow Drops, things shift in unexpected ways and new paths are revealed.\n\nMallow Drops is a meditative mix of platformer and a sliding block puzzle coming to Steam in 2016",
        96.0
    )

    val testPlatforms = listOf(
        Platform(6, "Atari 8-bit", 373),
        Platform(14, "Wii-U", 151),
        Platform(39, "xbox 360", 120),
    ).associateBy { it.id }

    val testPlatformsLogo = listOf(
        PlatformLogo(6, "//images.igdb.com/igdb/image/upload/t_logo_med/plad.jpg"),
        PlatformLogo(14, "//images.igdb.com/igdb/image/upload/t_logo_med/pl6n.jpg"),
        PlatformLogo(39, "//images.igdb.com/igdb/image/upload/t_logo_med/plha.jpg"),
    ).associateBy { it.id }

    GameDetailsPannel(
        testGame, testCovers, testGenres, testPlatforms, testPlatformsLogo, PaddingValues.Zero
    )
}

