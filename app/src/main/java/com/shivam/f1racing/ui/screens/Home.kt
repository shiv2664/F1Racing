package com.shivam.f1racing.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import com.shivam.f1racing.R
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.shivam.f1racing.ui.data.Driver


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 1.dp, 0.dp, 1.dp)
        ) {

            val driver = Driver(
                driverId = "max-verstappen",
                podiums = 11,
                points = 321,
                poles = 7,
                position = 3,
                teamId = "red-bull",
                wins = 5,
                firstName = "Max",
                lastName = "Verstappen",
                driverCode = "VER",
                teamName = "Red Bull",
                racingNumber = 1
            )

            MovieDetailsContent(driver)

            /*            when (val state = detailState) {
                            is NetworkResult.Loading -> {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            }

                            is NetworkResult.Success -> {
                                state.data?.let { movie ->
                                    MovieDetailsContent(movie = movie)
                                } ?: run {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        Text("Movie details not found.")
                                    }
                                }
                            }

                            is NetworkResult.Error -> {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text("Error: ${state.message ?: "Unknown error"}")
                                }
                            }
                        }*/
        }
    }
}


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@Preview
fun MovieDetailsContent(
    driver: Driver = Driver(
        driverId = "max-verstappen",
        podiums = 11,
        points = 321,
        poles = 7,
        position = 3,
        teamId = "red-bull",
        wins = 5,
        firstName = "Max",
        lastName = "Verstappen",
        driverCode = "VER",
        teamName = "Red Bull",
        racingNumber = 1
    )
) {

    Column(modifier = Modifier.fillMaxSize()) {
        // Backdrop Image with a gradient overlay

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFF5A08))
        ) {
            val (box1, text1, image, statsBox,gradient) = createRefs()
            Box(
                modifier = Modifier
                    .constrainAs(box1) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 32.dp)
                    }
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.2f),
                                Color.White.copy(alpha = 0.1f)
                            )
                        )
                    )
                    .border(
                        width = 1.5.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.5f),
                                Color.White.copy(alpha = 0.2f)
                            )
                        ),
                        shape = RoundedCornerShape(30.dp)
                    )
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.diamond),
                        contentDescription = "",
                        modifier = Modifier.size(14.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Get Pro",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = "Land",
                fontSize = 164.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFF2AF),
                modifier = Modifier
                    .constrainAs(text1) {
                        top.linkTo(box1.bottom)
                        start.linkTo(parent.start, margin = 32.dp)
                    }
                    .padding(start = 16.dp) // same start margin, below Surface
            )

            Image(
                painter = painterResource(id = R.drawable.driver_image),
                contentDescription = "Backdrop",
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(text1.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth()
                    .width(380.dp)
                    .height(400.dp)
                    .offset(x = 100.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.constrainAs(statsBox) {
                start.linkTo(parent.start, margin = 20.dp)
                top.linkTo(text1.bottom, margin = 30.dp)
            }) {

                ConstraintLayout {

                    val (pos, posText, wins, winsText, ptsText) = createRefs()

                    Image(
                        painter = painterResource(id = R.drawable.pos),
                        contentDescription = "",
                        modifier = Modifier
                            .constrainAs(pos) {
                                start.linkTo(parent.start)
                                top.linkTo(posText.top)
                                bottom.linkTo(posText.bottom)
                            }
                            .size(14.dp),
                    )

                    Text(
                        modifier = Modifier.constrainAs(posText) {
                            start.linkTo(pos.end, margin = 2.dp)
                            top.linkTo(parent.top)
                        },
                        text = "01 POS",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Image(
                        modifier = Modifier
                            .constrainAs(wins) {
                                start.linkTo(posText.end, margin = 20.dp)
                                top.linkTo(winsText.top)
                                bottom.linkTo(winsText.bottom)
                            }
                            .size(14.dp),
                        painter = painterResource(id = R.drawable.wins),
                        contentDescription = ""
                    )

                    Text(
                        modifier = Modifier.constrainAs(winsText) {
                            start.linkTo(wins.end, margin = 2.dp)
                            top.linkTo(parent.top)
                        },
                        text = "09 Wins",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                }

                ConstraintLayout() {

                    val (pts, ptsLabel) = createRefs()

                    // Define at the top of your file or in a separate file



                    Text(
                        text = "429",
                        modifier = Modifier.constrainAs(pts) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        },
                        style = TextStyle(
                            fontSize = 72.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 72.sp,
                            fontFamily = FontFamily(Font(R.font.space_regular, FontWeight.Normal)),
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFFF2AF),
                                    Color(0xFFFF5A08)
                                )
                            )
                        )
                    )


                    Box(
                        modifier = Modifier.constrainAs(ptsLabel){
                            start.linkTo(pts.end)
                            bottom.linkTo(pts.bottom, margin = (16).dp)
                        }
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFFF5A08)).padding(6.dp)

                    ) {
                        Text(
                            text = "PTS",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

            }

            Box(
                modifier = Modifier.constrainAs(gradient){
                    bottom.linkTo(parent.bottom)
                }
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.8f)
                            )
                        )
                    )
            )



        }

    }
}