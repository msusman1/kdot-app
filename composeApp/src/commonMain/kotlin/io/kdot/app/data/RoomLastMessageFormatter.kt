package io.kdot.app.data

import io.kdot.app.matrix.extensions.extractedDisplayName
import net.folivo.trixnity.client.store.TimelineEvent
import net.folivo.trixnity.core.model.UserId
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.FileBased
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.Location
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.TextBased
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.Unknown
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.VerificationRequest
import net.folivo.trixnity.core.model.events.m.room.bodyWithoutFallback

private fun timelineEventTypeDescription(event: TimelineEvent): String =
    event.content?.getOrNull().let { content ->
        when (content) {
            !is RoomMessageEventContent -> ""
            is FileBased.Image -> "Image"
            is FileBased.Video -> "Video"
            is FileBased.Audio -> "Audio"
            is FileBased.File -> "File"
            is TextBased, is Location, is VerificationRequest, is Unknown -> content.bodyWithoutFallback

            else -> ""
        }
    }
class RoomLastMessageFormatter() {
    fun format(event: TimelineEvent, isDirect: Boolean, me: UserId): String {
        val senderName = event.event.sender.extractedDisplayName
        val message = timelineEventTypeDescription(event)
        val isByMe = me == event.event.sender
        val sender = if (isByMe) {
            "you"
        } else {
            senderName
        }
        return if (isDirect && isByMe.not()) message
        else if (message.isNotEmpty()) "${sender}: $message"
        else ""
    }
}

