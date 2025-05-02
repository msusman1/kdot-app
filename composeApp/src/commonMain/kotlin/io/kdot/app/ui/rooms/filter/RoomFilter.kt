package io.kdot.app.ui.rooms.filter

enum class RoomFilter(val readable: String) {
    Unread("Unread"),
    People("People"),
    Rooms("Rooms"),
    Favourites("Favourites"),
    Invites("Invites");

    val incompatibleFilters: Set<RoomFilter>
        get() = when (this) {
            Rooms -> setOf(People, Invites)
            People -> setOf(Rooms, Invites)
            Unread -> setOf(Invites)
            Favourites -> setOf(Invites)
            Invites -> setOf(Rooms, People, Unread, Favourites)
        }
}