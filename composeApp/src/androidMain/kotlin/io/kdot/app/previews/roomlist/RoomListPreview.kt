package io.kdot.app.previews.roomlist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.element.android.features.roomlist.impl.model.RoomSummaryDisplayType
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.components.avatar.AvatarSize
import io.kdot.app.designsystem.utils.snackbar.SnackbarMessage
import io.kdot.app.domain.AvatarData
import io.kdot.app.domain.RoomListRoomSummary
import io.kdot.app.features.leaveroom.LeaveRoomState
import io.kdot.app.previews.KDotPreview
import io.kdot.app.previews.PreviewDayNight
import io.kdot.app.ui.roomlist.MatrixUser
import io.kdot.app.ui.roomlist.RoomListContentState
import io.kdot.app.ui.roomlist.RoomListScreenInternal
import io.kdot.app.ui.roomlist.RoomListState
import io.kdot.app.ui.roomlist.filter.RoomListFilterState
import io.kdot.app.ui.roomlist.search.RoomListSearchState
import net.folivo.trixnity.core.model.UserId
import kotlin.concurrent.atomics.ExperimentalAtomicApi


internal fun aRoomsContentState(
    summaries: List<RoomListRoomSummary> = aRoomListRoomSummaryList(),
) = RoomListContentState.Rooms(
    summaries = summaries,
)


internal fun aSkeletonContentState() = RoomListContentState.Skeleton(16)

internal fun anEmptyContentState() = RoomListContentState.Empty


fun aRoomListSearchState(
    isSearchActive: Boolean = false,
    query: String = "",
    results: List<RoomListRoomSummary> = listOf(),
    isRoomDirectorySearchEnabled: Boolean = false,
) = RoomListSearchState(
    isSearchActive = isSearchActive,
    query = query,
    results = results,
    isRoomDirectorySearchEnabled = isRoomDirectorySearchEnabled,

    )

fun aLeaveRoomState(
    confirmation: LeaveRoomState.Confirmation = LeaveRoomState.Confirmation.Hidden,
    progress: LeaveRoomState.Progress = LeaveRoomState.Progress.Hidden,
    error: LeaveRoomState.Error = LeaveRoomState.Error.Hidden,
) = LeaveRoomState(
    confirmation = confirmation,
    progress = progress,
    error = error,
)


open class RoomListStateProvider : PreviewParameterProvider<RoomListState> {
    override val values: Sequence<RoomListState>
        get() = sequenceOf(
            aRoomListState(),
            @OptIn(ExperimentalAtomicApi::class)
            (aRoomListState(
        snackbarMessage = SnackbarMessage(
            Resources.String.common_verification_complete
        )
    )),
            aRoomListState(hasNetworkConnection = false),
            aRoomListState(contextMenu = aContextMenuShown(roomName = null)),
            aRoomListState(contextMenu = aContextMenuShown(roomName = "A nice room name")),
            aRoomListState(contextMenu = aContextMenuShown(isFavorite = true)),
            aRoomListState(contentState = aRoomsContentState()),
            aRoomListState(contentState = anEmptyContentState()),
            aRoomListState(contentState = aSkeletonContentState()),
            aRoomListState(
                searchState = aRoomListSearchState(
                    isSearchActive = true,
                    query = "Test"
                )
            ),
            aRoomListState(
                contentState = aRoomsContentState(),
            )
        )
}

internal fun aRoomListState(
    matrixUser: MatrixUser = MatrixUser(userId = UserId("@id:domain"), displayName = "User#1"),
    showAvatarIndicator: Boolean = false,
    hasNetworkConnection: Boolean = true,
    snackbarMessage: SnackbarMessage? = null,
    contextMenu: RoomListState.ContextMenu = RoomListState.ContextMenu.Hidden,
    leaveRoomState: LeaveRoomState = aLeaveRoomState(),
    searchState: RoomListSearchState = aRoomListSearchState(),
    filtersState: RoomListFilterState = aRoomListFiltersState(),
    contentState: RoomListContentState = aRoomsContentState(),
) = RoomListState(
    matrixUser = matrixUser,
    showAvatarIndicator = showAvatarIndicator,
    hasNetworkConnection = hasNetworkConnection,
    snackbarMessage = snackbarMessage,
    contextMenu = contextMenu,
    leaveRoomState = leaveRoomState,
    filterState = filtersState,
    searchState = searchState,
    contentState = contentState,

    )

internal fun aRoomListRoomSummaryList(): List<RoomListRoomSummary> {
    return listOf(
        aRoomListRoomSummary(
            name = "Room Invited",
            avatarData = AvatarData(
                "!roomId",
                "Room with Alice and Bob",
                size = AvatarSize.RoomListItem
            ),
            id = "!roomId:domain",
            inviteSender = anInviteSender(),
            displayType = RoomSummaryDisplayType.INVITE,
        ),
        aRoomListRoomSummary(
            name = "Room",
            numberOfUnreadMessages = 1,
            timestamp = "14:18",
            lastMessage = "A very very very very long message which suites on two lines",
            avatarData = AvatarData("!id", "R", size = AvatarSize.RoomListItem),
            id = "!roomId:domain",
        ),
        aRoomListRoomSummary(
            name = "Room#2",
            numberOfUnreadMessages = 0,
            timestamp = "14:16",
            lastMessage = "A short message",
            avatarData = AvatarData("!id", "Z", size = AvatarSize.RoomListItem),
            id = "!roomId2:domain",
        ),
        aRoomListRoomSummary(
            id = "!roomId3:domain",
            displayType = RoomSummaryDisplayType.PLACEHOLDER,
        ),
        aRoomListRoomSummary(
            id = "!roomId4:domain",
            displayType = RoomSummaryDisplayType.PLACEHOLDER,
        ),
    )
}

@Composable
@PreviewDayNight
fun RoomsPreview(@PreviewParameter(RoomListStateProvider::class) state: RoomListState) {
    KDotPreview {
        RoomListScreenInternal(
            state = state,
            eventSinkContextMenu = {},
            eventSinkLeaveRoom = {},
            eventSinkRoomList = {},
            eventSinkRoomListSearch = {},
            eventSinkRoomListFilter = {},
            snackBarMessage = null,
            roomListFilterState = emptySet(),
            leaveRoomState = LeaveRoomState.Default,
            onRoomClick = {},
            onSettingsClick = {},
            onCreateRoomClick = {},
            onRoomSettingsClick = {},
            onRoomDirectorySearchClick = {},

            )
    }
}