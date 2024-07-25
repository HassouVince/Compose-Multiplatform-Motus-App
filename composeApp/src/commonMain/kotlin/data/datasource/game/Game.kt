package data.datasource.game

import commons.utils.json
import data.datasource.words.Word
import kotlinx.serialization.Serializable

@Serializable
data class Game(
    var word: Word?,
    val userEntries: MutableList<String> = mutableListOf(),
    var winCount: Int = 0,
    var loseCount: Int = 0,
) {
    fun toJson() = json.encodeToString(serializer(),this)
}