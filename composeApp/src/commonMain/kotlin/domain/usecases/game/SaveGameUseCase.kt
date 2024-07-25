package domain.usecases.game

import data.datasource.game.Game
import data.repositories.GameRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SaveGameUseCase(
    private val gameRepository: GameRepository,
    private val coroutineContext: CoroutineContext,
) {
    suspend operator fun invoke(game: Game) =
        save(game)

    private suspend fun save(game: Game) = withContext(coroutineContext) {
        gameRepository.saveGame(game)
    }
}