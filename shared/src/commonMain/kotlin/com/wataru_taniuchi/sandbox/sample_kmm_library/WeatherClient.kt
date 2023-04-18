package com.wataru_taniuchi.sandbox.sample_kmm_library

import ApiClient
import io.ktor.client.call.*
import io.ktor.client.request.*

class WeatherClient {
    private val apiClient = ApiClient()

    suspend fun get(prefecture: Prefecture): WeatherData {
        val result = apiClient.client.get("https://weather.tsukumijima.net/api/forecast/city/${prefecture.id}")
        return result.body()
    }
}