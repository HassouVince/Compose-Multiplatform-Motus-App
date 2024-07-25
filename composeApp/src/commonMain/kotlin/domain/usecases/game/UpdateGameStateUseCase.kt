package domain.usecases.game

import data.datasource.game.Game
import data.datasource.game.toGameState
import data.datasource.game.updateWith
import data.repositories.GameRepository
import data.repositories.WordsRepository
import domain.models.GameState
import domain.transformResult
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class UpdateGameStateUseCase(
    private val gameRepository: GameRepository,
    private val wordsRepository: WordsRepository,
    private val coroutineContext: CoroutineContext,
) {
    suspend operator fun invoke(game: Game, lastGameState: GameState) =
            update(game, lastGameState)

    private suspend fun update(game: Game, lastGameState: GameState) = withContext(coroutineContext) {
        wordsRepository.getWordToPlay(game)
            .transformResult {
                flow {
                    emit(
                        game.apply {
                            game.updateWith(it, lastGameState)
                            gameRepository.saveGame(this)
                        }.toGameState()
                    )
                }
            }
    }
}