package leegroup.module.photosample.data.remote.services

import leegroup.module.photosample.data.models.PhotoModel
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApiService {

    @GET("photos")
    suspend fun getPhotos(
        @Query("id") ids: List<Int>?,
        @Query("title_like") titleLike: String?,
        @Query("_start") since: Int,
        @Query("_limit") limit: Int,
    ): List<PhotoModel>
}
