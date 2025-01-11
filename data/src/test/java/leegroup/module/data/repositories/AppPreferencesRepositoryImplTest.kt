package leegroup.module.data.repositories

import androidx.datastore.preferences.core.Preferences
import app.cash.turbine.test
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import leegroup.module.data.local.datastore.AppDataStore
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AppPreferencesRepositoryImplTest {

    private lateinit var appPreferencesRepository: AppPreferencesRepositoryImpl
    private lateinit var mockAppDataStore: AppDataStore

    @Before
    fun setUp() {
        mockAppDataStore = mockk()
        appPreferencesRepository = AppPreferencesRepositoryImpl(mockAppDataStore)
    }

    @Test
    fun `getAppPreference returns true when value is null`() = runTest {
        // Arrange
        every { mockAppDataStore.getValue(any<Preferences.Key<Boolean>>()) } returns flowOf(null)

        // Act & Assert
        appPreferencesRepository.getAppPreference().test {
            assertEquals(true, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAppPreference returns stored value when not null`() = runTest {
        // Arrange
        every { mockAppDataStore.getValue(any<Preferences.Key<Boolean>>()) } returns flowOf(false)

        // Act & Assert
        appPreferencesRepository.getAppPreference().test {
            assertEquals(false, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateAppPreference calls AppDataStore setValue with correct values`() = runTest {
        // Arrange
        val newPreferenceValue = false
        coEvery {
            mockAppDataStore.setValue(
                any<Preferences.Key<Boolean>>(),
                newPreferenceValue
            )
        } just Runs

        // Act
        appPreferencesRepository.updateAppPreference(newPreferenceValue)

        // Assert
        coVerify { mockAppDataStore.setValue(any<Preferences.Key<Boolean>>(), newPreferenceValue) }
    }
}
