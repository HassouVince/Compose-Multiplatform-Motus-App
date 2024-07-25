package commons.utils

sealed class AppResult<T> {
    data class Success<T>(val data: T) : AppResult<T>()
    class Error<T>(val throwable: Throwable) : AppResult<T>()
    class Loading<T> : AppResult<T>()
    class None<T> : AppResult<T>()
}