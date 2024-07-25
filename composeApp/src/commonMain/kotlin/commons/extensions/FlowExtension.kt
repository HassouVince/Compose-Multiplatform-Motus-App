package commons.extensions

import commons.utils.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.mapInResult(): Flow<AppResult<T>>{
    return safeFlow {
        AppResult.Success(it)
    }.onStart { emit(AppResult.Loading()) }
}

fun<T, S> Flow<T>.safeFlow(block : suspend (T) -> AppResult<S>) : Flow<AppResult<S>> {
    return flow {
        this@safeFlow
            .map { block(it) }
            .catch {
                emit(AppResult.Error(it))
            }.collect{
                emit(it)
            }
    }
}

fun<T1, T2, R> combineInResult(
    flow: Flow<AppResult<T1>>,
    flow2: Flow<AppResult<T2>>,
    transform: suspend (T1, T2) -> R
): Flow<AppResult<R>> {
    return combine(flow, flow2) { p1, p2 ->
        if(p1 is AppResult.Success && p2 is AppResult.Success)
            AppResult.Success(transform(p1.data, p2.data))
        else if(p1 is AppResult.Loading || p2 is AppResult.Loading)
            AppResult.Loading()
        else if (p1 is AppResult.Error)
            AppResult.Error(p1.throwable)
        else if (p2 is AppResult.Error)
            AppResult.Error(p2.throwable)
        else
            throw IllegalArgumentException()
    }
}