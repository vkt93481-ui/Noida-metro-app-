package com.noidametro.app.data

import com.noidametro.app.model.RouteSummary
import com.noidametro.app.model.Station
import kotlin.math.*

object AquaLineRepository {

    val stations: List<Station> = listOf(
        Station(0, "Noida Sector 51", "NS51", 28.5877, 77.3728, "Interchange walk to Delhi Metro Blue Line (Sec 52)"),
        Station(1, "Noida Sector 50", "NS50", 28.5732, 77.3775),
        Station(2, "Noida Sector 76", "NS76", 28.5673, 77.3818),
        Station(3, "Noida Sector 101", "NS101", 28.5567, 77.3878),
        Station(4, "Noida Sector 81", "NS81", 28.5494, 77.3924),
        Station(5, "NSEZ", "NSEZ", 28.5387, 77.3991, "Noida Special Economic Zone"),
        Station(6, "Noida Sector 83", "NS83", 28.5305, 77.4055),
        Station(7, "Noida Sector 137", "NS137", 28.5105, 77.4101),
        Station(8, "Noida Sector 142", "NS142", 28.5008, 77.4119, "Advant Navis / Express Highway"),
        Station(9, "Noida Sector 143", "NS143", 28.4932, 77.4184),
        Station(10, "Noida Sector 144", "NS144", 28.4862, 77.4256),
        Station(11, "Noida Sector 145", "NS145", 28.4785, 77.4342),
        Station(12, "Noida Sector 146", "NS146", 28.4718, 77.4418),
        Station(13, "Noida Sector 147", "NS147", 28.4631, 77.4522),
        Station(14, "Noida Sector 148", "NS148", 28.4550, 77.4660),
        Station(15, "Knowledge Park II", "KP02", 28.4568, 77.5006, "India Expo Mart / Colleges"),
        Station(16, "Pari Chowk", "PRCK", 28.4651, 77.5104, "Greater Noida Hub"),
        Station(17, "Alpha 1", "ALP1", 28.4728, 77.5138),
        Station(18, "Delta 1", "DLT1", 28.4820, 77.5236),
        Station(19, "GNIDA Office", "GNDA", 28.4883, 77.5302),
        Station(20, "Depot Station", "DEPT", 28.4889, 77.5427, "Terminal Station")
    )

    fun calculateRoute(fromIndex: Int, toIndex: Int): RouteSummary {
        val from = stations[fromIndex]
        val to = stations[toIndex]
        val routeStations = if (fromIndex <= toIndex) {
            stations.subList(fromIndex, toIndex + 1)
        } else {
            stations.subList(toIndex, fromIndex + 1).reversed()
        }

        val stops = abs(toIndex - fromIndex)
        val estimatedMinutes = max(3, (stops * 2.4).roundToInt())

        val normalFare = when {
            stops <= 1 -> 10
            stops == 2 -> 15
            stops in 3..6 -> 20
            stops in 7..9 -> 30
            stops in 10..16 -> 40
            else -> 50
        }

        val holidayFare = when {
            stops <= 2 -> 10
            stops in 3..6 -> 15
            stops in 7..9 -> 20
            stops in 10..16 -> 30
            else -> 40
        }

        val direction = when {
            toIndex > fromIndex -> "Towards Depot Station (Greater Noida)"
            toIndex < fromIndex -> "Towards Noida Sector 51"
            else -> "Same Station"
        }

        return RouteSummary(
            from = from,
            to = to,
            stationsOnRoute = routeStations,
            stops = stops,
            estimatedMinutes = estimatedMinutes,
            tokenFareNormal = normalFare,
            smartCardFareNormal = normalFare * 0.9,
            tokenFareHoliday = holidayFare,
            smartCardFareHoliday = holidayFare * 0.9,
            directionLabel = direction
        )
    }

    fun distanceMeters(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371000.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
        return 2 * r * asin(sqrt(a))
    }

    fun findNearestStation(lat: Double, lon: Double): Pair<Station, Double> {
        var nearest = stations.first()
        var minDist = Double.MAX_VALUE
        for (station in stations) {
            val d = distanceMeters(lat, lon, station.lat, station.lon)
            if (d < minDist) {
                minDist = d
                nearest = station
            }
        }
        return nearest to minDist
    }
}
