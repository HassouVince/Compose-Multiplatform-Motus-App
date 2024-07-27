package domain.usecases.game

import data.datasource.game.Game
import data.datasource.game.getEntryLimit
import data.datasource.game.isEntryMatches
import data.repositories.WordsRepository
import domain.models.EntryState
import kotlinx.coroutines.runBlocking

class VerifyWordUseCase(
    private val wordsRepository: WordsRepository,
) {
    operator fun invoke(game: Game, value: String) =
        getEntryStateFromValue(game, value)

    private fun getEntryStateFromValue(
        game: Game,
        value: String
    ): EntryState = runBlocking {
        if (value.length < game.getEntryLimit())
            EntryState.InvalidLengthEntry()
        else {
            wordsRepository.getSameWordOrNull(value)
                ?.let {
                    if (game.isEntryMatches(value))
                        EntryState.Success(value)
                    else EntryState.WrongEntry(value)
                } ?: run { EntryState.UnknownEntry(value) }
        }
    }
}