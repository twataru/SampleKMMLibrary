package com.wataru_taniuchi.sandbox.sample_kmm_library

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform