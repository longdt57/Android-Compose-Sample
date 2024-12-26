package leegroup.module.domain.usecases

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class StrategyUseCaseTest {

    private lateinit var useCase: TestStrategyUseCase

    @Before
    fun setup() {
        useCase = TestStrategyUseCase()
    }

    @Test
    fun `ONLINE_ONLY strategy should fetch data from remote`() = runTest {
        // Arrange
        val param = "testParam"
        val remoteData = "Remote Data"
        useCase.remoteResponse = flowOf(remoteData)

        // Act
        val result = useCase(param, StrategyUseCase.Strategy.ONLINE_ONLY).toList()

        // Assert
        assertEquals(listOf(remoteData), result)
    }

    @Test
    fun `LOCAL_ONLY strategy should fetch data from local`() = runTest {
        // Arrange
        val param = "testParam"
        val localData = "Local Data"
        useCase.localResponse = flowOf(localData)

        // Act
        val result = useCase(param, StrategyUseCase.Strategy.LOCAL_ONLY).toList()

        // Assert
        assertEquals(listOf(localData), result)
    }

    @Test
    fun `ONLINE_BACKWARD_LOCAL strategy should fallback to local on remote error`() = runTest {
        // Arrange
        val param = "testParam"
        val localData = "Fallback Local Data"
        useCase.remoteResponse = flow { throw Exception("Remote failure") }
        useCase.localResponse = flowOf(localData)

        // Act
        val result = useCase(param, StrategyUseCase.Strategy.ONLINE_BACKWARD_LOCAL).toList()

        // Assert
        assertEquals(listOf(localData), result)
    }

    @Test
    fun `LOCAL_BACKWARD_ONLINE strategy should fetch local first and fallback to remote`() = runTest {
        // Arrange
        val param = "testParam"
        val remoteData = "Remote Data"
        useCase.localResponse = flow { throw Exception("Local failure") }
        useCase.remoteResponse = flowOf(remoteData)

        // Act
        val result = useCase(param, StrategyUseCase.Strategy.LOCAL_BACKWARD_ONLINE).toList()

        // Assert
        assertEquals(listOf(remoteData), result)
    }

    @Test
    fun `LOCAL_BACKWARD_ONLINE strategy should fetch valid local data`() = runTest {
        // Arrange
        val param = "testParam"
        val localData = "Valid Local Data"
        useCase.localResponse = flowOf(localData)
        useCase.remoteResponse = flowOf("Remote Data")

        // Act
        val result = useCase(param, StrategyUseCase.Strategy.LOCAL_BACKWARD_ONLINE).toList()

        // Assert
        assertEquals(listOf(localData), result)
    }

    @Test
    fun `getAndCheckLocal should return null for invalid local data`() = runTest {
        // Arrange
        val invalidLocalData = "Invalid Data"
        val remoteData = "Remote Data"
        useCase.localResponse = flowOf(invalidLocalData)
        useCase.remoteResponse = flowOf(remoteData)
        useCase.setIsLocalValid(false)

        // Act
        val result = useCase.invoke("getAndCheckLocal", StrategyUseCase.Strategy.LOCAL_BACKWARD_ONLINE).first()

        // Assert
        assertEquals(remoteData, result)
    }

    // Mock implementation of StrategyUseCase
    private class TestStrategyUseCase : StrategyUseCase<String, String>() {
        var remoteResponse: Flow<String> = flowOf()
        var localResponse: Flow<String> = flowOf()
        private var isLocalValidFlag = true

        override fun getRemote(param: String): Flow<String> = remoteResponse

        override fun getLocal(param: String): Flow<String> = localResponse

        override suspend fun isLocalValid(value: String): Boolean = isLocalValidFlag

        fun setIsLocalValid(isValid: Boolean) {
            isLocalValidFlag = isValid
        }
    }
}
