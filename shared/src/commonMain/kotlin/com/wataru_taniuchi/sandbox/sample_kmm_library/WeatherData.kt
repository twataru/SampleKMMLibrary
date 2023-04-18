package com.wataru_taniuchi.sandbox.sample_kmm_library

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    val publicTime: String?,
    val publicTimeFormatted: String?,
    val publishingOffice: String?,
    val title: String?,
    val link: String?,
    val description: Description?,
    val forecasts: List<Forecasts>?,
    val location: Location?,
    val copyright: Copyright?,
)

@Serializable
data class Description(
    val publicTime: String?,
    val publicTimeFormatted: String?,
    val headlineText: String?,
    val bodyText: String?,
)

@Serializable
data class Forecasts (
    val date: String,
    val dateLabel: String,
    val telop: String,
    val detail: Detail,
    val temperature: Temperature,
    val chanceOfRain: ChanceOfRain,
    val image: Image,

    )

@Serializable
data class Detail (
    val weather: String,
    val wind: String,
    val wave: String
)

@Serializable
data class Temperature(
    val min: TemperatureValue,
    val max: TemperatureValue
)

@Serializable
data class TemperatureValue(
    val celsius: String?,
    val fahrenheit: String?
)

@Serializable
data class ChanceOfRain(
    @SerialName("T00_06") val T0006: String?,
    @SerialName("T06_12") val T0612: String?,
    @SerialName("T12_18") val T1218: String?,
    @SerialName("T18_24") val T1824: String?,
)

@Serializable
data class Image(
    val title: String?,
    val url: String?,
    val width: Int,
    val height: Int,
)

@Serializable
data class Location(
    val area: String?,
    val prefecture: String?,
    val district: String?,
    val city: String?,
)

@Serializable
data class Copyright(
    val title: String?,
    val link: String?,
    val image: Image?,
    val provider: List<Provider>?,
)

@Serializable
data class Provider(
    val link: String?,
    val name: String?,
    val note: String?,
)