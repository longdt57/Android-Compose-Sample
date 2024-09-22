package com.app.androidcompose.support.application

import android.content.Context
import android.os.RemoteException
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.app.androidcompose.data.local.preferences.EncryptedSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReferrerClientHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val encryptedSharedPreferences: EncryptedSharedPreferences,
) {

    private lateinit var referrerClient: InstallReferrerClient

    private fun getReferrer() {
        referrerClient = InstallReferrerClient.newBuilder(context).build()
        CoroutineScope(Dispatchers.IO).launch {
            referrerClient.startConnection(
                object : InstallReferrerStateListener {

                    override fun onInstallReferrerSetupFinished(responseCode: Int) {
                        when (responseCode) {
                            InstallReferrerClient.InstallReferrerResponse.OK -> {
                                try {
                                    saveReferrer(referrerClient.installReferrer)
                                } catch (exception: RemoteException) {
                                    saveReferrer(null)
                                }
                            }

                            else -> {
                                saveReferrer(null)
                            }
                        }
                        referrerClient.endConnection()
                    }

                    override fun onInstallReferrerServiceDisconnected() {
                        saveReferrer(null)
                        referrerClient.endConnection()
                    }
                },
            )
        }
    }

    private fun saveReferrer(data: ReferrerDetails?) {
        encryptedSharedPreferences.referrer = data?.installReferrer.orEmpty()
        encryptedSharedPreferences.appInstallTime = data?.installBeginTimestampSeconds
    }
}