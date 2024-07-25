package domain.usecases.game

import data.datasource.game.Game
import data.datasource.game.toGameState
import data.repositories.GameRepository
import data.repositories.WordsRepository
import domain.models.GameState
import domain.transformResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class GetGameStateUseCase(
    private val gameRepository: GameRepository,
    private val wordRepository: WordsRepository,
    private val coroutineContext: CoroutineContext,
) {
    suspend operator fun invoke() = getGameState()

    private suspend fun getGameState(): Flow<GameState> = withContext(coroutineContext) {
        gameRepository.getGame()
            .transformResult {
                it?.word?.let { _ ->
                    flowOf(it.toGameState())
                } ?: buildGameState()
            }
    }

    private suspend fun buildGameState(): Flow<GameState> = wordRepository.getWordToPlay()
        .transformResult {
            flowOf(
                GameState.Playing(
                    Game(word = it).apply {
                        gameRepository.saveGame(this@apply)
                    }
                )
            )
        }
}