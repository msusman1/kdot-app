package io.kdot.app.data

import io.kdot.app.matrix.extensions.extractedDisplayName
import net.folivo.trixnity.client.store.Room
import net.folivo.trixnity.client.store.RoomDisplayName
import net.folivo.trixnity.core.model.UserId


class RoomNameFormatter() {
    fun format(room: Room): String {
        val roomName: RoomDisplayName? = room.name
        val roomNameToUse = if (roomName != null) {
            val (explicitName, heroes, otherUsersCount, roomIsEmpty) = roomName
            val users: List<UserId> = heroes
            when {
                !explicitName.isNullOrEmpty() -> explicitName
                heroes.isEmpty() -> {
                    when {
                        roomIsEmpty -> "Empty chat"
                        otherUsersCount > 1 -> "$otherUsersCount persons"
                        else -> room.roomId.full
                    }
                }

                else -> {
                    val heroConcat = users.mapIndexed { index: Int, userId: UserId? ->
                        when {
                            otherUsersCount == 0 && index < heroes.size - 2 || otherUsersCount > 0L && index < heroes.size - 1 -> {
                                nameFromHeroes(heroes, index) + ", "
                            }

                            otherUsersCount == 0 && index == heroes.size - 2 -> {
                                nameFromHeroes(heroes, index) + " And "
                            }

                            otherUsersCount > 0L && index == heroes.size - 1 -> {
                                nameFromHeroes(heroes, index) + " And ${otherUsersCount} others"
                            }

                            else -> {
                                nameFromHeroes(heroes, index)
                            }
                        }
                    }.joinToString("")
                    if (roomIsEmpty) "Empty chat (was ${heroConcat})"
                    else heroConcat
                }
            }
        } else {
            room.roomId.full
        }

        return roomNameToUse
    }
}

fun nameFromHeroes(
    heroes: List<UserId>, index: Int
) = heroes[index].extractedDisplayName

