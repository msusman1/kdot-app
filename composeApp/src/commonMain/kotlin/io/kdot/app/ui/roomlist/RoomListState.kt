package io.kdot.app.ui.roomlist

import androidx.compose.runtime.Immutable
import io.kdot.app.designsystem.components.avatar.AvatarSize
import io.kdot.app.domain.AvatarData
import io.kdot.app.domain.RoomListRoomSummary
import net.folivo.trixnity.core.model.RoomId
import net.folivo.trixnity.core.model.UserId

data class RoomListState(
    val matrixUser: MatrixUser,
    val showAvatarIndicator: Boolean,
    val hasNetworkConnection: Boolean,
    val contextMenu: ContextMenu,
    val filtersState: RoomListFiltersState,
    val searchState: RoomListSearchState,
    val contentState: RoomListContentState,
    val acceptDeclineInviteState: AcceptDeclineInviteState,
    val directLogoutState: DirectLogoutState,
    val eventSink: (RoomListEvents) -> Unit,
){
    val displayFilters = contentState is RoomListContentState.Rooms


    sealed interface ContextMenu {
        data object Hidden : ContextMenu
        data class Shown(
            val roomId: RoomId,
            val roomName: String?,
            val isDm: Boolean,
            val isFavorite: Boolean,
            val markAsUnreadFeatureFlagEnabled: Boolean,
            val eventCacheFeatureFlagEnabled: Boolean,
            val hasNewContent: Boolean,
        ) : ContextMenu
    }
}

data class MatrixUser(
    val userId: UserId,
    val displayName: String? = null,
    val avatarUrl: String? = null,
)

enum class SecurityBannerState {
    None,
    SetUpRecovery,
    RecoveryKeyConfirmation,
}

fun MatrixUser.getAvatarData(size: AvatarSize) = AvatarData(
    id = userId.full,
    name = displayName,
    url = avatarUrl,
    size = size,
)

fun MatrixUser.getBestName(): String {
    return displayName?.takeIf { it.isNotEmpty() } ?: userId.value
}
@Immutable
sealed interface RoomListContentState {
    data class Skeleton(val count: Int) : RoomListContentState
    data class Empty(
        val securityBannerState: SecurityBannerState,
    ) : RoomListContentState
    data class Rooms(
        val securityBannerState: SecurityBannerState,
        val summaries: List<RoomListRoomSummary>,
    ) : RoomListContentState
}
