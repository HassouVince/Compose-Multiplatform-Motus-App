package data.datasource.game

import commons.extensions.isCharMatchesAtPosition
import data.datasource.words.Word
import domain.models.EntryState
import domain.models.GameState
import domain.models.GameState.Companion.ATTEMPTS_LIMIT

private const val INVALID_CHAR = '_'

fun Game.toGameState(): GameState {
    return when {
        isEntryMatches() -> GameState.LevelSuccess(this)
        userEntries.size >= ATTEMPTS_LIMIT -> GameState.LevelFailure(this)
        else -> GameState.Playing(this)
    }
}

fun Game.validateEntry(entryState: EntryState): GameState {
    return when(entryState) {
        is EntryState.InvalidLengthEntry -> GameState.Playing(this.copy())
        else -> this.copy().apply {
            addEntry(entryState)
        }.toGameState()
    }
}

fun Game.addEntry(entryState: EntryState) {
    userEntries.add(
        if (entryState is EntryState.UnknownEntry)
            entryState.entry.orEmpty().replace("[A-Za-z]".toRegex(), INVALID_CHAR.toString())
        else
            entryState.entry.orEmpty()
    )
}

fun Game.getHiddenWord() = word?.value?.let { word ->
    val letters = mutableListOf<Char>()
    word.onEachIndexed { i, char ->
        letters.add(
            if (userEntries.none { item -> word.isCharMatchesAtPosition(item[i], i) }) '?'
            else char
        )
    }
    word.first() + letters.joinToString("").substring(1)
}.orEmpty()

fun Game.updateWith(newWord: Word, gameState: GameState) {
    word = newWord
    userEntries.clear()
    if(gameState is GameState.LevelSuccess)
        winCount++
    else if(gameState is GameState.LevelFailure)
        loseCount++
}

fun Game?.getEntryLimit() =
    this?.word?.value?.length ?: GameState.DEFAULT_WORDS_LENGTH

fun Game.isEntryMatches(value: String? = null): Boolean =
    word?.value?.uppercase() ==
            (value?.uppercase() ?: userEntries.lastOrNull()?.uppercase())