package data.datasource.words

import kotlinx.serialization.Serializable

@Serializable
class Word(
    val value: String,
    var used: Boolean = false
) {
    init {
        value.trim()
    }

    override fun hashCode(): Int {
        var result = used.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

    override fun toString(): String {
        return "Word(used=$used, value='$value')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as Word
        if (value != other.value) return false
        if (used != other.used) return false
        return true
    }
}