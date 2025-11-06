package com.shivam.f1racing.ui.data

import kotlinx.serialization.Serializable

@Serializable
data class RaceDetails(
    val schedule: List<Schedule>
)

@Serializable
data class Schedule(
    val circuitId: String,
    val isSprint: Boolean,
    val podium: List<String>?,
    val raceEndTime: Int,
    val raceId: String,
    val raceName: String,
    val raceStartTime: Int,
    val raceState: String,
    val round: Int,
    val sessions: List<Session>
)

@Serializable
data class Session(
    val _id: String,
    val endTime: Int,
    val sessionId: String,
    val sessionName: String,
    val sessionState: String,
    val sessionType: String,
    val startTime: Int
)