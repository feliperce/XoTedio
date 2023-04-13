package br.com.mobileti.xotedio.data.remote.extension

import br.com.mobileti.xotedio.data.Resource
import br.com.mobileti.xotedio.data.ErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


fun <REMOTE, MAPPER> callNetworkData(
    remote: suspend () -> Response<REMOTE>,
    mapper: suspend (REMOTE?) -> MAPPER,
    onFinish: suspend () -> Unit = { },
    onRemoteFail: suspend FlowCollector<Resource<MAPPER>>.(code: Int) -> Unit? = { }
) = flow<Resource<MAPPER>> {
    val remoteCall = remote()

    if (remoteCall.isSuccessful) {
        val remoteData = remoteCall.body()

        if (remoteData != null) {
            emit(Resource.Success(mapper(remoteData)))
        } else {
            emit(Resource.Error(errorType = ErrorType.GENERIC))
        }
    } else {
        val remoteFailed = onRemoteFail(remoteCall.code())

        if (remoteFailed == null) {
            emit(Resource.Error(errorType = ErrorType.GENERIC))
        }
    }
}.flowOn(Dispatchers.Default)
.catch {
    when (it) {
        is UnknownHostException -> {
            emit(Resource.Error(errorType = ErrorType.CONNECTION))
        }
        is TimeoutException -> {
            emit(Resource.Error(errorType = ErrorType.TIMEOUT))
        }
        else -> {
            emit(Resource.Error(errorType = ErrorType.GENERIC))
        }
    }
}.onCompletion {
    emit(Resource.Loading(false))
    onFinish()
}.onStart {
    emit(Resource.Loading(true))
}