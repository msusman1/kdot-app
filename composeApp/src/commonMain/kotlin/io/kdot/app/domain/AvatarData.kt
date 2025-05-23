package io.kdot.app.domain

import androidx.compose.runtime.Immutable
import io.kdot.app.designsystem.components.avatar.AvatarSize


@Immutable
data class AvatarData(
    val id: String,
    val name: String?,
    val url: String? = null,
    val size: AvatarSize,
) {
    val initial by lazy {
        // For roomIds, use "#" as initial
        (name?.takeIf { it.isNotBlank() } ?: id.takeIf { !it.startsWith("!") } ?: "#")
            .let { dn ->
                var startIndex = 0
                val initial = dn[startIndex]

                if (initial in listOf('@', '#', '+') && dn.length > 1) {
                    startIndex++
                }

                var next = dn[startIndex]

                // LEFT-TO-RIGHT MARK
                if (dn.length >= 2 && 0x200e == next.code) {
                    startIndex++
                    next = dn[startIndex]
                }

                while (next.isWhitespace()) {
                    if (dn.length > startIndex + 1) {
                        startIndex++
                        next = dn[startIndex]
                    } else {
                        break
                    }
                }



                when {
                    // If no boundary was found, default to the next char if possible
                    startIndex + 1 <= dn.length -> dn.substring(startIndex, startIndex + 1)
                    // Return a fallback character otherwise
                    else -> "#"
                }
            }
            .uppercase()
    }
}

fun AvatarData.getBestName(): String {
    return name?.takeIf { it.isNotEmpty() } ?: id
}
