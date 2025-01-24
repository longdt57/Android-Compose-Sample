package com.example.note.domain.usecases

import com.example.note.domain.repositories.CloudRepository
import javax.inject.Inject

internal class SyncFromCloudUseCase @Inject constructor(
    private val cloudRepository: CloudRepository
) {

    suspend operator fun invoke(): Boolean {
        if (cloudRepository.isSyncedFromCloud()) return false
        return cloudRepository.syncFromCloud()
    }
}