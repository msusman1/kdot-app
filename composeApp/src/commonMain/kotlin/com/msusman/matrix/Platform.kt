package com.msusman.matrix

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform