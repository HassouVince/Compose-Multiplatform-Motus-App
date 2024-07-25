package data.repositories

import commons.extensions.mapInResult
import commons.utils.AppResult
import data.datasource.game.Game
import data.datasource.game.GameLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GameRepository {
    suspend fun saveGame(game: Game): Flow<AppResult<Unit>>
    suspend fun getGame(): Flow<AppResult<Game?>>
}

class GameRepositoryImpl(
    private val gameLocalDataSource: GameLocalDataSource,
) : GameRepository {
    override suspend fun saveGame(game: Game): Flow<AppResult<Unit>> =
        flow {
            emit(gameLocalDataSource.saveGame(game))
        }.mapInResult()

    override suspend fun getGame(): Flow<AppResult<Game?>> =
        flow {
            emit(gameLocalDataSource.getGame()
                .apply { println("Current word => ${this?.word}")  }
            )
        }.mapInResult()
}