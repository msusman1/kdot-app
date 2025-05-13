package io.kdot.app.previews.roomlist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.kdot.app.previews.KDotPreview
import io.kdot.app.ui.roomlist.RoomListModalBottomSheetContent
import io.kdot.app.ui.roomlist.RoomListState
import net.folivo.trixnity.core.model.RoomId


open class RoomListStateContextMenuShownProvider :
    PreviewParameterProvider<RoomListState.ContextMenu.Shown> {
    override val values: Sequence<RoomListState.ContextMenu.Shown>
        get() = sequenceOf(
            aContextMenuShown(hasNewContent = true),
            aContextMenuShown(isDm = true),
            aContextMenuShown(roomName = null)
        )
}

internal fun aContextMenuShown(
    roomName: String? = "aRoom",
    isDm: Boolean = false,
    hasNewContent: Boolean = false,
    isFavorite: Boolean = false,
) = RoomListState.ContextMenu.Shown(
    roomId = RoomId("!aRoom:aDomain"),
    roomName = roomName,
    isDm = isDm,
    markAsUnreadFeatureFlagEnabled = true,
    hasNewContent = hasNewContent,
    isFavorite = isFavorite,
)

@PreviewLightDark
@Composable
private fun RoomListModelBottomSheetPreview(
    @PreviewParameter(RoomListStateContextMenuShownProvider::class) contextMenu: RoomListState.ContextMenu.Shown
) {
    KDotPreview {
        RoomListModalBottomSheetContent(
            contextMenu = contextMenu,
            onRoomMarkReadClick = {},
            onRoomMarkUnreadClick = {},
            onRoomSettingsClick = {},
            onLeaveRoomClick = {},
            onFavoriteChange = {},
            )
    }
}