package domain.usecases.game

import commons.extensions.combineInResult
import commons.utils.AppResult
import data.datasource.game.Game
import data.repositories.GameRepository
import data.repositories.WordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ClearGameUseCase(
    private val gameRepository: GameRepository,
    private val wordsRepository: WordsRepository,
    private val coroutineContext: CoroutineContext,
) {
    suspend operator fun invoke() =
        clear()

    private suspend fun clear(): Flow<AppResult<Unit?>> =
        withContext(coroutineContext)  {
            combineInResult(
                wordsRepository.removeAll(),
                gameRepository.saveGame(Game(word = null))
            ) { _, _ ->
                null
            }
        }
}