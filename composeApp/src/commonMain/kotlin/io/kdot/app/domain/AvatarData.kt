package io.kdot.app.domain

data class AvatarData(
    val displayName: String?,
    val avatar: ByteArray
) {
    val initials: String
        get() = displayName?.firstOrNull()?.uppercase() ?: "#"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as AvatarData

        if (displayName != other.displayName) return false
        if (!avatar.contentEquals(other.avatar)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = displayName.hashCode()
        result = 31 * result + avatar.contentHashCode()
        return result
    }

}