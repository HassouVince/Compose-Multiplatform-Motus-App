package domain

import commons.utils.AppResult
import domain.models.GameState
import domain.models.GameState.Companion.mapToGameState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<AppResult<T>>.transformResult(
    onSuccess: suspend (value: T) -> Flow<GameState>,
) : Flow<GameState> {
    return this.flatMapLatest {
        if(it is AppResult.Error)
            flowOf(GameState.Error(it.throwable))
        if(it is AppResult.Success)
            onSuccess(it.data)
        else
            flowOf(it.mapToGameState())
    }
}