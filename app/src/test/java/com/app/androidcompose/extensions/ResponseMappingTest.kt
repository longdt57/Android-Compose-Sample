package com.app.androidcompose.extensions

import com.app.androidcompose.MockUtil
import com.app.androidcompose.data.extensions.flowTransform
import com.app.androidcompose.data.model.User
import com.app.androidcompose.domain.exceptions.ApiException
import com.app.androidcompose.domain.exceptions.NoConnectivityException
import io.kotest.matchers.shouldBe
import java.io.IOException
import java.io.InterruptedIOException
import java.net.UnknownHostException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
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
                    MockUtil.errorResponse,
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
