package leegroup.module.data.extensions

import io.kotest.matchers.shouldBe
import java.io.IOException
import java.io.InterruptedIOException
import java.net.UnknownHostException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import leegroup.module.data.MockUtil
import leegroup.module.data.remote.models.responses.User
import leegroup.module.domain.exceptions.ApiException
import leegroup.module.domain.exceptions.NoConnectivityException
import org.junit.Test

@ExperimentalCoroutinesApi
class ResponseMappingTest {

    @Test
    fun `When mapping API request flow failed with UnknownHostException, it returns mapped NoConnectivityException error`() =
        runTest {
            flowTransform<Unit> {
                throw UnknownHostException()
            }.catch {
                it shouldBe NoConnectivityException
            }.collect()
        }

    @Test
    fun `When mapping API request flow failed with InterruptedIOException, it returns mapped NoConnectivityException error`() =
        runTest {
            flowTransform<User> {
                throw InterruptedIOException()
            }.catch {
                it shouldBe NoConnectivityException
            }.collect()
        }

    @Test
    fun `When mapping API request flow failed with HttpException, it returns mapped ApiException error`() =
        runTest {
            val httpException = MockUtil.mockHttpException
            flowTransform<User> {
                throw httpException
            }.catch {
                it shouldBe ApiException(
                    MockUtil.error,
                    httpException.code(),
                    httpException.message()
                )
            }.collect()
        }

    @Test
    fun `When mapping API request flow failed with unhandled exceptions, it should throw that error`() =
        runTest {
            val exception = IOException("Canceled")
            flowTransform<User> {
                throw exception
            }.catch {
                it shouldBe exception
            }.collect()
        }
}
