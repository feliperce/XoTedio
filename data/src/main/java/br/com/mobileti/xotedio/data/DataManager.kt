package br.com.mobileti.xotedio.data

sealed class Resource<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: ErrorType? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(isLoading: Boolean) : Resource<T>(isLoading = isLoading)
    class Error<T>(errorType: ErrorType, data: T? = null) : Resource<T>(error = errorType, data = data)
}

enum class ErrorType(val errorMsg: Int) {
    NONE(-1),
    CONNECTION(R.string.connection_error),
    TIMEOUT(R.string.timeout_error),
    GENERIC(R.string.generic_error)
}
