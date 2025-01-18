package leegroup.module.photosample.domain.repositories

import kotlinx.coroutines.flow.Flow

interface PhotoFavoriteRepository {

    fun getFavoriteList(): Flow<Set<Int>>
    suspend fun saveFavoriteList(ids: Set<Int>)
}