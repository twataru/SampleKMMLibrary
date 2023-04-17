package com.wataru_taniuchi.sandbox.sample_kmm_library

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}