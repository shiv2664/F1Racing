package com.shivam.f1racing.ui.screens.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.loader.content.Loader
import com.shivam.f1racing.R
import com.shivam.f1racing.data.NetworkResult
import com.shivam.f1racing.ui.data.DriverDetails
import com.shivam.f1racing.ui.data.RaceDetails
import com.shivam.f1racing.ui.data.Schedule
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.Unit
import androidx.core.net.toUri


@Composable
fun AutoPager(
    innerPadding: PaddingValues,
    detailState: NetworkResult<DriverDetails>?,
) {
    AutoScrollingPager(
        pages = listOf(
            { FirstItem(innerPadding, detailState) },
            { SecondItem(innerPadding) },
        ),
        delayMillis = 3000
    )
}


@Composable
fun AutoScrollingPager(
    pages: List<@Composable () -> Unit>,
    delayMillis: Long = 3000
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { Int.MAX_VALUE })
    val scope = rememberCoroutineScope()

    // auto-scroll
    LaunchedEffect(pagerState.currentPage) {
        delay(delayMillis)
        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) { index ->
            val actualPage = index % pages.size
            pages[actualPage]()
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- Indicator ---
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val actualPage = pagerState.currentPage % pages.size
            repeat(pages.size) { index ->
                val isSelected = index == actualPage
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (isSelected) 10.dp else 6.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) Color.White else Color.White.copy(alpha = 0.4f)
                        )
                )
            }
        }
    }
}


@Composable
fun GetPro(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(
                top = innerPadding.calculateTopPadding() + 12.dp,
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 20.dp
            )
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
}

@Composable
fun FirstItem(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    detailState: NetworkResult<DriverDetails>?
) {

    when (val state = detailState) {
        is NetworkResult.Loading -> {
            Loader(innerPadding)
        }

        is NetworkResult.Success -> {
            state.data?.let { drivers ->

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFF5A08)))
                {
                    val (box1, text1, image, statsBox, gradient) = createRefs()
                    val driver = drivers.drivers.firstOrNull { it.position == 1 }

                    Text(
                        text = driver?.firstName ?: "",
                        fontSize = 164.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.space_regular, FontWeight.Normal)),
                        color = Color(0xFFFFF2AF),
                        modifier = Modifier
                            .constrainAs(text1) {
                                top.linkTo(box1.bottom)
                                start.linkTo(parent.start, margin = 16.dp)
                            }
                            .padding(
                                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 8.dp,
                                top = innerPadding.calculateTopPadding() + 10.dp
                            ) // same start margin, below Surface
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
                            .offset(x = 100.dp)
                            .padding(
                                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                                top = innerPadding.calculateTopPadding()
                            ),
                        contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier.constrainAs(statsBox) {
                        start.linkTo(parent.start, margin = 20.dp)
                        top.linkTo(text1.bottom, margin = (-20).dp)
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
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily(Font(R.font.space_regular)),
                                            color = Color.White
                                        )
                                    ) {
                                        append("${driver?.position} ")
                                    }
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.White
                                        )
                                    ) {
                                        append("POS")
                                    }
                                },
                                modifier = Modifier.constrainAs(posText) {
                                    start.linkTo(pos.end, margin = 2.dp)
                                    top.linkTo(parent.top)
                                },
                                fontFamily = FontFamily(Font(R.font.space_regular))
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
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily(Font(R.font.space_regular)),
                                            color = Color.White
                                        )
                                    ) {
                                        append("${driver?.wins} ")
                                    }
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.White
                                        )
                                    ) {
                                        append("Wins")
                                    }
                                },
                                modifier = Modifier.constrainAs(winsText) {
                                    start.linkTo(wins.end, margin = 2.dp)
                                    top.linkTo(parent.top)
                                },
                                fontFamily = FontFamily(Font(R.font.space_regular))
                            )

                        }

                        ConstraintLayout {

                            val (pts, ptsLabel) = createRefs()

                            Text(
                                text = "${driver?.points}",
                                modifier = Modifier.constrainAs(pts) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                },
                                style = TextStyle(
                                    fontSize = 72.sp,
                                    fontWeight = FontWeight.Thin,
                                    lineHeight = 72.sp,
                                    fontFamily = FontFamily(
                                        Font(
                                            R.font.space_regular,
                                            FontWeight.Normal
                                        )
                                    ),
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFFFFF2AF),
                                            Color(0xFFFF5A08)
                                        )
                                    )
                                )
                            )


                            Box(
                                modifier = Modifier
                                    .constrainAs(ptsLabel) {
                                        start.linkTo(pts.end, margin = 10.dp)
                                        bottom.linkTo(pts.bottom, margin = (16).dp)
                                    }
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFFF5A08))
                                    .padding(6.dp)

                            ) {
                                Text(
                                    text = "PTS",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(
                                        Font(
                                            R.font.space_regular,
                                            FontWeight.Normal
                                        )
                                    ),
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }

                    }

                    Box(
                        modifier = Modifier
                            .constrainAs(gradient) {
                                bottom.linkTo(parent.bottom)
                            }
                            .fillMaxWidth()
                            .height(100.dp)
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

        null -> {
            Loader(innerPadding)
        }
    }

}

@Composable
fun SecondItem(innerPadding: PaddingValues = PaddingValues(0.dp)) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.Black),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(274.dp)
                .height(274.dp)
                .padding(innerPadding),
            painter = painterResource(id = R.drawable.insta_image),
            contentDescription = ""
        )

        Button(
            onClick = { },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF86FF0E),
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Follow Us",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.space_regular))
            )
        }
    }
}


@Composable
fun RaceEvents(onNavigateToDetail: (Schedule?) -> Unit, raceState: NetworkResult<RaceDetails>?) {

    val context = LocalContext.current

    when (val state = raceState) {
        is NetworkResult.Loading -> {
            Loader()
        }

        is NetworkResult.Error -> {}
        is NetworkResult.Success -> {

            state.data?.let { raceDetails ->

                val currentTime = System.currentTimeMillis() / 1000

                val nextRace = raceDetails.schedule
                    .filter { it.raceStartTime > currentTime }
                    .minByOrNull { it.raceStartTime }

                val nextSession = nextRace?.sessions
                        ?.filter { it.startTime > currentTime }
                    ?.minByOrNull { it.startTime }

                val instant = Instant.ofEpochSecond(nextSession?.startTime?.toLong() ?: 0)
                val zoned = instant.atZone(ZoneId.systemDefault())

                val day = zoned.format(DateTimeFormatter.ofPattern("dd EEEE"))     // Mon, Tue, Fri
                val time = zoned.format(DateTimeFormatter.ofPattern("hh:mm a")) // 06:30 PM


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                )
                {

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(130.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF044331))
                            .clickable {
                                onNavigateToDetail(nextRace)
                            }
                            .padding(12.dp),
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = "${nextSession?.sessionName}",
                                    color = Color.White.copy(alpha = 0.8f),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontFamily = FontFamily(Font(R.font.space_regular)),
                                    fontWeight = FontWeight.Bold
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.circuit_small),
                                    contentDescription = "Circuit",
                                    modifier = Modifier.size(32.dp),
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.calender),
                                    contentDescription = "Calendar",
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "$day",
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = FontFamily(Font(R.font.space_regular)),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = "$time",
                                    color = Color(0xFF02BB81),
                                    fontSize = 28.sp,
                                    fontFamily = FontFamily(Font(R.font.space_regular)),
                                    fontWeight = FontWeight.Bold
                                )
                                /*Text(
                                    text = "AM",
                                    color = Color(0xFF02BB81).copy(alpha = 0.7f),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(Font(R.font.space_regular)),
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )*/
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(130.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Red Distance Box
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.Black),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .height(80.dp)
                                    .background(Color(0xFFF91D26)),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.distance),
                                    contentDescription = "Distance Logo",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.padding(start = 8.dp),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = "7015.3",
                                    color = Color.White,
                                    fontFamily = FontFamily(Font(R.font.space_regular)),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "km",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.space_regular)),
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(top = 3.dp, start = 2.dp)
                                )
                            }
                        }
                        // Blue Education Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFF3020FD))
                                .clickable{
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        "https://blog.boxbox.club/tagged/beginners-guide".toUri()
                                    )
                                    context.startActivity(intent)
                                }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                                val (dots, title, arrow) = createRefs()

                                // 3 dots - vertically centered with text
                                Image(
                                    painter = painterResource(id = R.drawable.dots_3),
                                    contentDescription = "F1 Education Logo",
                                    modifier = Modifier
                                        .height(20.dp)
                                        .constrainAs(dots) {
                                            start.linkTo(parent.start)
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                        }
                                )


                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily(Font(R.font.space_regular)),
                                                color = Color.White
                                            )
                                        ) {
                                            append("Formula 1\n")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        ) {
                                            append("Education")
                                        }
                                    },
                                    modifier = Modifier.constrainAs(title) {
                                        start.linkTo(dots.end, margin = 8.dp)
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                    },
                                    fontFamily = FontFamily(Font(R.font.space_regular))
                                )

                                Image(
                                    painter = painterResource(id = R.drawable.arrow_up_right),
                                    contentDescription = "Go",
                                    modifier = Modifier
                                        .size(16.dp)
                                        .constrainAs(arrow) {
                                            top.linkTo(parent.top)
                                            end.linkTo(parent.end)
                                        }
                                )
                            }
                        }
                    }
                }



            }

        }
        null ->{ Loader()
        }

    }
}



@Composable
fun LewisImage() {
    val context=LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable{
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    "https://www.instagram.com/boxbox_club/".toUri()
                )
                context.startActivity(intent)
            }
            .padding(16.dp)
    ) {
        // Rounded image
        Image(
            painter = painterResource(R.drawable.lewis_image),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Image(
            painter = painterResource(id = R.drawable.instagram),
            contentDescription = "Calendar",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
                .size(32.dp)
        )
    }
}

@Composable
fun Loader(innerPadding: PaddingValues = PaddingValues(0.dp)) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            strokeWidth = 4.dp,
            color = Color.White,
        )
    }
}


