package leegroup.module.photosample.domain.repositories

import kotlinx.coroutines.flow.Flow

interface PhotoListFavoriteFilterRepository {

    fun getFavoriteFilter(): Flow<Boolean>
    suspend fun setFavoriteFilter(value: Boolean)
}