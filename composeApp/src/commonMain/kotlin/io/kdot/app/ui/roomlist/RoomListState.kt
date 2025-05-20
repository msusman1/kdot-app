package io.kdot.app.ui.roomlist

import androidx.compose.runtime.Immutable
import io.kdot.app.designsystem.components.avatar.AvatarSize
import io.kdot.app.designsystem.utils.snackbar.SnackbarMessage
import io.kdot.app.domain.AvatarData
import io.kdot.app.domain.RoomListRoomSummary
import io.kdot.app.features.leaveroom.LeaveRoomState
import io.kdot.app.ui.roomlist.filter.RoomListFilterState
import io.kdot.app.ui.roomlist.search.RoomListSearchState
import net.folivo.trixnity.core.model.RoomId
import net.folivo.trixnity.core.model.UserId

data class RoomListState(
    val matrixUser: MatrixUser,
    val showAvatarIndicator: Boolean,
    val hasNetworkConnection: Boolean,
    val contextMenu: ContextMenu,
    val contentState: RoomListContentState,
    val leaveRoomState: LeaveRoomState,
    val snackbarMessage: SnackbarMessage?,
    val filterState: RoomListFilterState,
    val searchState: RoomListSearchState,
) {
    companion object {
        val Default = RoomListState(
            matrixUser = MatrixUser(userId = UserId("!id:domain")),
            showAvatarIndicator = true,
            hasNetworkConnection = true,
            contextMenu = ContextMenu.Hidden,
            contentState = RoomListContentState.Skeleton(16),
            leaveRoomState = LeaveRoomState(
                confirmation = LeaveRoomState.Confirmation.Hidden,
                progress = LeaveRoomState.Progress.Hidden,
                error = LeaveRoomState.Error.Hidden,
            ),
            snackbarMessage = null,
            filterState = emptySet(),
            searchState = RoomListSearchState(
                isSearchActive = false,
                query = "",
                results = emptyList(),
                isRoomDirectorySearchEnabled = false
            )
        )
    }

    val displayFilters = contentState is RoomListContentState.Rooms


    sealed interface ContextMenu {
        data object Hidden : ContextMenu
        data class Shown(
            val roomId: RoomId,
            val roomName: String?,
            val isDm: Boolean,
            val isFavorite: Boolean,
            val markAsUnreadFeatureFlagEnabled: Boolean,
            val hasNewContent: Boolean,
        ) : ContextMenu
    }
}

data class MatrixUser(
    val userId: UserId,
    val displayName: String? = null,
    val avatarUrl: String? = null,
)


fun MatrixUser.getAvatarData(size: AvatarSize) = AvatarData(
    id = userId.full,
    name = displayName,
    url = avatarUrl,
    size = size,
)

fun MatrixUser.getBestName(): String {
    return displayName?.takeIf { it.isNotEmpty() } ?: userId.full
}

@Immutable
sealed interface RoomListContentState {
    data class Skeleton(val count: Int) : RoomListContentState
    data object Empty : RoomListContentState
    data class Rooms(
        val summaries: List<RoomListRoomSummary>,
    ) : RoomListContentState
}
