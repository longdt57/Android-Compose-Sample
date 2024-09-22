package com.app.androidcompose.data.extensions

import com.app.androidcompose.data.remote.models.responses.ErrorResponse
import com.app.androidcompose.domain.exceptions.ApiException
import com.app.androidcompose.domain.exceptions.NoConnectivityException
import com.app.androidcompose.domain.exceptions.ServerException
import com.app.androidcompose.support.util.JsonUtil
import java.io.IOException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import kotlin.experimental.ExperimentalTypeInference
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalTypeInference::class)
fun <T> flowTransform(@BuilderInference block: suspend FlowCollector<T>.() -> T) = flow {
    runCatching { block() }
        .onSuccess { result -> emit(result) }
        .onFailure { exception -> throw exception.mapError() }
}

private fun Throwable.mapError(): Throwable {
    return when (this) {
        is UnknownHostException,
        is SSLException,
        is InterruptedIOException -> NoConnectivityException

        is ConnectException -> ServerException

        is HttpException -> {
            val errorResponse = parseErrorResponse(response())
            ApiException(
                errorResponse,
                code(),
                message()
            )
        }

        else -> this
    }
}

private fun parseErrorResponse(response: Response<*>?): ErrorResponse? {
    val jsonString = response?.errorBody()?.string() ?: return null
    return try {
        JsonUtil.decodeFromString<ErrorResponse>(jsonString)
    } catch (exception: IOException) {
        null
    } catch (ex: SerializationException) {
        null
    } catch (ex: IllegalArgumentException) {
        null
    } catch (ex: Exception) {
        null
    }
}
