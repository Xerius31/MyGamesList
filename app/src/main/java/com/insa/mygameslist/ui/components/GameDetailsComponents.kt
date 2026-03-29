package com.insa.mygameslist.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.insa.mygameslist.R
import com.insa.mygameslist.domain.model.Game
import com.insa.mygameslist.ui.theme.MyGamesListTheme

@Composable
fun GameDetailsPannel(
    game: Game?, innerPadding: PaddingValues
) {
    game?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header with Cover and Title
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                AsyncImage(
                    model = "https:${game.coverUrl?.replace("t_thumb", "t_cover_big")}",
                    placeholder = painterResource(R.drawable.cover_placeholder),
                    error = painterResource(R.drawable.cover_placeholder),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                                startY = 400f
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = game.name,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            modifier = Modifier.weight(1f)
                        )

                        var isFavorite by remember { mutableStateOf(game.isFavorite) }
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                                .clickable {
                                    isFavorite = !isFavorite
                                    game.isFavorite = isFavorite
                                }, contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = if (isFavorite) R.drawable.ic_star_full else R.drawable.ic_star_empty),
                                contentDescription = if (isFavorite) "favorite" else "not favorite",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                // Genres
                Text(
                    text = game.genreNames.joinToString(", "),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Platforms
                Text(
                    text = "Platforms",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(game.platformsNames) { index, name ->
                        Card(
                            shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (index < game.platformsLogoUrl.size) {
                                    AsyncImage(
                                        model = "https:${
                                            game.platformsLogoUrl[index].replace(
                                                "t_thumb", "t_logo_med"
                                            )
                                        }",
                                        contentDescription = null,
                                        modifier = Modifier.size(36.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                                Text(text = name, fontSize = 12.sp)
                            }
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                // Summary
                Text(
                    text = "Summary",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = game.summary ?: "No description available.",
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Game Not Found", style = MaterialTheme.typography.headlineMedium)
    }
}


@Preview(showBackground = true)
@Composable
fun GameDetailsPreview() {

    val testGame = Game(
        id = 25910,
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big/it58smbpvhqhbbubqsj5.jpg",
        firstReleaseDate = 1478131200,
        name = "Mallow Drops",
        summary = "Mallow Drops is a gravity puzzle where two kiwis regather their eggs in a shattered world. Help Marsh and Mallow rescue their eggs and get to the exit! Turn everything upside down as you slide, shift and move through the tricky world of Mallow Drops, a mix of platformer and a sliding block puzzle. Getting to where you need to go half the fun - just be sure to have a safe landing!\n\nWith Wooly Jumpers hopping about and Dirty Underbears surprising you suddenly, it won’t be easy, and if you’re not careful, the dreaded Dropbears may get the drop on you!\n\nCHANGE YOUR WORLD BY CHANGING YOUR PERSPECTIVE\nYou move in straight lines. Your world turns upside-down.\n\nWhen you turn the world of Mallow Drops, things shift in unexpected ways and new paths are revealed.\n\nMallow Drops is a meditative mix of platformer and a sliding block puzzle coming to Steam in 2016",
        totalRating = 96.0,

        genreNames = listOf("Puzzle", "Indie"),

        platformsNames = listOf("Atari 8-bit", "Wii-U", "Xbox 360"),
        platformsLogoUrl = listOf(
            "//images.igdb.com/igdb/image/upload/t_logo_med/plad.jpg",
            "//images.igdb.com/igdb/image/upload/t_logo_med/pl6n.jpg",
            "//images.igdb.com/igdb/image/upload/t_logo_med/plha.jpg"
        )
    )

    MyGamesListTheme {
        GameDetailsPannel(
            testGame, PaddingValues.Zero
        )
    }
}
