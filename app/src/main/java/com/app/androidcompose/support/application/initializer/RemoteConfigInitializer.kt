package com.app.androidcompose.support.application.initializer

import android.content.Context
import androidx.startup.Initializer
import com.app.androidcompose.data.repository.RemoteConfigRepository

class RemoteConfigInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        RemoteConfigRepository.fetch()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}