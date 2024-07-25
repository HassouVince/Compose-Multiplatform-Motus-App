package commons.extensions

import commons.utils.AppResult

fun <T> AppResult<T>.onSuccess(action: (T) -> Unit) = when(this){
    is AppResult.Success -> apply { action(data) }
    is AppResult.Error -> this
    is AppResult.Loading -> this
    is AppResult.None -> this
}

fun <T> AppResult<T>.onError(action: (Throwable) -> Unit) = when(this){
    is AppResult.Success -> this
    is AppResult.Error -> apply { action(throwable) }
    is AppResult.Loading -> this
    is AppResult.None -> this
}