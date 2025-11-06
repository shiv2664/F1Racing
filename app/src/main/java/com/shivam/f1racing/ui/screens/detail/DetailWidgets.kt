package com.shivam.f1racing.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.shivam.f1racing.R
import com.shivam.f1racing.ui.data.Session
import com.shivam.f1racing.ui.data.Schedule
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun UpcomingRaceDetails(innerPadding: PaddingValues = PaddingValues(0.dp), schedule: Schedule?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF009B3A).copy(alpha = 0.8f),
                        Color.Black
                    )
                )
            )
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.wrapContentSize().padding(top = innerPadding.calculateTopPadding()+10.dp)){
            Text(
                text = "Upcoming race",
                fontFamily = FontFamily(Font(R.font.space_regular)),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (verticalAlignment = Alignment.CenterVertically ){

            Column(modifier = Modifier.weight(1f)) {

                Column(
                    modifier = Modifier
                ) {
                    Text(
                        text = "${schedule?.round}",
                        fontFamily = FontFamily(Font(R.font.space_regular)),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${schedule?.raceName}",
                        fontFamily = FontFamily(Font(R.font.montserat_bold)),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "São Paulo",
                        fontFamily = FontFamily(Font(R.font.space_regular)),
                        color = Color(0xFF009B3A),
                        fontSize = 14.sp
                    )
                    Text(
                        text = formatDateRange(schedule?.raceStartTime?.toLong()?:0,schedule?.raceEndTime?.toLong()?:0),
                        fontFamily = FontFamily(Font(R.font.space_regular)),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }


                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "FP1 Starts in",
                    fontFamily = FontFamily(Font(R.font.space_regular)),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    val remaining = getTimeRemaining(schedule?.raceStartTime?.toLong()?:0)
                    val dayText = "${remaining.first}"
                    val hourText = "${remaining.second}"
                    val minText = "${remaining.third}"

                    CountdownUnit(dayText, "Days")
                    CountdownUnit(hourText, "Hours")
                    CountdownUnit(minText, "Minutes")
                }
            }

            Image(
                painter = painterResource(id = R.drawable.circuit),
                contentDescription = "São Paulo Circuit",
                modifier = Modifier
                    .size(200.dp)
                    .padding(start = 8.dp)
                    .weight(1f)
            )
        }


    }
}

@Composable
fun CountdownUnit(value: String, label: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = value,
            fontFamily = FontFamily(Font(R.font.space_regular)),
            color = Color(0xFF009B3A),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
        Text(
            text = label,
            fontFamily = FontFamily(Font(R.font.space_regular)),
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
    }
}

fun formatDateRange(start: Long, end: Long): String {
    val zone = ZoneId.systemDefault()
    val formatter = DateTimeFormatter.ofPattern("dd MMM")

    val startDate = Instant.ofEpochSecond(start).atZone(zone).format(formatter)
    val endDate = Instant.ofEpochSecond(end).atZone(zone).format(formatter)

    return "$startDate to $endDate"
}

fun getTimeRemaining(startTime: Long): Triple<Long, Long, Long> {
    val now = System.currentTimeMillis() / 1000
    var diff = startTime - now

    if (diff <= 0) return Triple(0, 0, 0)

    val days = diff / (24 * 3600)
    diff %= (24 * 3600)

    val hours = diff / 3600
    diff %= 3600

    val minutes = diff / 60

    return Triple(days, hours, minutes)
}

