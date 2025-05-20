package io.kdot.app.matrix.extensions

import io.kdot.app.ui.roomlist.MatrixUser
import net.folivo.trixnity.clientserverapi.client.UserApiClient
import net.folivo.trixnity.core.model.UserId


suspend fun UserApiClient.getUserAvatar(userId: UserId): MatrixUser {
    val avatarUrl = this.getAvatarUrl(userId = userId).getOrNull()
    val displayName = this.getDisplayName(userId = userId).getOrNull()
   return MatrixUser(
        userId = userId, displayName = displayName, avatarUrl = avatarUrl
    )

}