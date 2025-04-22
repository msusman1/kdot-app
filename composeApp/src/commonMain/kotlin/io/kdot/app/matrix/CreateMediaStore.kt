package io.kdot.app.matrix

import net.folivo.trixnity.client.media.MediaStore
import org.koin.core.module.Module

fun interface CreateMediaStore {
    suspend operator fun invoke(): MediaStore
}

expect fun platformCreateMediaStoreModule(): Module
