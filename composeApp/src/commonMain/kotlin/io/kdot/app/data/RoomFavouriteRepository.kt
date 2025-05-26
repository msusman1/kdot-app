package io.kdot.app.data

import io.kdot.app.matrix.MatrixClientProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import net.folivo.trixnity.core.model.RoomId
import net.folivo.trixnity.core.model.UserId
import net.folivo.trixnity.core.model.events.m.TagEventContent

interface RoomFavouriteRepository {
    fun checkRoomFav(roomId: RoomId): Flow<Boolean>
    suspend fun toggleFavourite(roomId: RoomId, isFavorite: Boolean)
}

class RoomFavouriteRepositoryImpl(
    clientProvider: MatrixClientProvider
) : RoomFavouriteRepository {

    val userId: UserId = clientProvider.getClient().userId
    val roomApiClient = clientProvider.getClient().api.room

    private val favouriteUpdates =
        MutableSharedFlow<Pair<RoomId, Boolean>>(extraBufferCapacity = 64)

    override fun checkRoomFav(roomId: RoomId): Flow<Boolean> = channelFlow {
        val initial = roomApiClient.getTags(userId = userId, roomId = roomId)
            .getOrNull()
            ?.tags
            ?.keys
            ?.any { it is TagEventContent.TagName.Favourite } == true
        send(initial)

        favouriteUpdates
            .filter { it.first == roomId }
            .map { it.second }
            .collect { send(it) }
    }

    override suspend fun toggleFavourite(
        roomId: RoomId, isFavorite: Boolean
    ) {
        if (isFavorite) {
            roomApiClient.setTag(
                userId = userId,
                roomId = roomId,
                tag = TagEventContent.TagName.Favourite.name,
                tagValue = TagEventContent.Tag(0.1)
            ).getOrThrow()
        } else {
            roomApiClient.deleteTag(
                userId = userId, roomId = roomId, tag = TagEventContent.TagName.Favourite.name
            ).getOrThrow()
        }
        favouriteUpdates.emit(roomId to isFavorite)
    }
}