package io.kdot.app.ui.roomlist.filter

sealed interface RoomListFilterEvents {
    data class Toggle(val roomListFilter: RoomListFilter) : RoomListFilterEvents
    data object Clear : RoomListFilterEvents
}