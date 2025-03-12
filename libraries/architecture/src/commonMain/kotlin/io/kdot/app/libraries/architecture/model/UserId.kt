package io.kdot.app.libraries.architecture.model

import kotlin.jvm.JvmInline

@JvmInline
value class UserId(val value: String) {
    val extractDisplayName: String
        get() = value
            .removePrefix("@")
            .substringBefore(":")
}