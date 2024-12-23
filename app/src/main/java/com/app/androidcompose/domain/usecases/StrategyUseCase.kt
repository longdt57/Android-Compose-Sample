package com.app.androidcompose.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

abstract class StrategyUseCase<Param, T> {

    operator fun invoke(param: Param, strategy: Strategy): Flow<T> {
        return when (strategy) {
            Strategy.ONLINE_ONLY -> getRemoteWithOnEachAction(param)
            Strategy.LOCAL_ONLY -> getLocal(param)
            Strategy.ONLINE_BACKWARD_LOCAL -> remoteFirst(param)
            Strategy.LOCAL_BACKWARD_ONLINE -> localFirst(param)
        }
    }

    protected abstract fun getRemote(param: Param): Flow<T>

    protected abstract fun getLocal(param: Param): Flow<T>

    protected open fun isLocalValid(value: T): Boolean = true

    protected open suspend fun onRemoteSuccess(param: Param, result: T) = Unit

    private fun getRemoteWithOnEachAction(param: Param): Flow<T> {
        return getRemote(param).onEach {
            onRemoteSuccess(param, it)
        }
    }


    private fun remoteFirst(param: Param): Flow<T> {
        return getRemoteWithOnEachAction(param)
            .catch { cause ->
                val localData = getAndCheckLocal(param)
                if (localData != null) {
                    emit(localData) // Emit local data if valid
                } else {
                    throw cause
                }
            }
    }

    private fun localFirst(param: Param): Flow<T> {
        return flow<T> {
            getAndCheckLocal(param = param)?.let {
                emit(it)
            } ?: error("Local data is invalid")
        }.catch {
            emitAll(getRemoteWithOnEachAction(param))
        }
    }

    private suspend fun getAndCheckLocal(param: Param): T? {
        return getLocal(param).firstOrNull()?.let { localData ->
            if (isLocalValid(localData)) {
                localData
            } else {
                null
            }
        }
    }

    enum class Strategy {
        ONLINE_ONLY, LOCAL_ONLY, ONLINE_BACKWARD_LOCAL, LOCAL_BACKWARD_ONLINE,
    }
}
