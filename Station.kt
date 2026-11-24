package com.noidametro.app.model

data class Station(
    val index: Int,
    val name: String,
    val code: String,
    val lat: Double,
    val lon: Double,
    val note: String = ""
)

data class RouteSummary(
    val from: Station,
    val to: Station,
    val stationsOnRoute: List<Station>,
    val stops: Int,
    val estimatedMinutes: Int,
    val tokenFareNormal: Int,
    val smartCardFareNormal: Double,
    val tokenFareHoliday: Int,
    val smartCardFareHoliday: Double,
    val directionLabel: String
)
