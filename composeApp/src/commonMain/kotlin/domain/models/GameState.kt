package domain.models

import commons.utils.NoWordsRemainingException
import commons.utils.AppResult
import data.datasource.game.Game
import data.datasource.game.toGameState

sealed class GameState(var game: Game? = null) {
    class Default: GameState()
    class Error(val t: Throwable): GameState()
    class Loading: GameState()
    class Playing(game: Game): GameState(game)
    class LevelSuccess(game: Game): GameState(game)
    class LevelFailure(game: Game): GameState(game)
    class Completed: GameState()

    companion object {
        const val DEFAULT_WORDS_LENGTH = 6
        const val ATTEMPTS_LIMIT = 7

        fun AppResult<*>.mapToGameState(): GameState {
            return if (this is AppResult.Success &&  this.data is Game)
                this.data.toGameState()
            else when (this) {
                is AppResult.Error ->
                    if(this.throwable is NoWordsRemainingException) Completed()
                    else Error(this.throwable)
                is AppResult.Loading -> Loading()
                else -> Default()
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        return true
    }
    override fun hashCode(): Int {
        return this::class.hashCode()
    }
}