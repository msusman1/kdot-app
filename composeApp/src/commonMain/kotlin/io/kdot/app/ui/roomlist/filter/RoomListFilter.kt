package io.kdot.app.ui.roomlist.filter

enum class RoomListFilter(val readable: String) {
    Unread("Unread"),
    People("People"),
    Rooms("Rooms"),
    Favourites("Favourites"),
    Invites("Invites");

    val incompatibleFilters: Set<RoomListFilter>
        get() = when (this) {
            Rooms -> setOf(People, Invites)
            People -> setOf(Rooms, Invites)
            Unread -> setOf(Invites)
            Favourites -> setOf(Invites)
            Invites -> setOf(Rooms, People, Unread, Favourites)
        }
}