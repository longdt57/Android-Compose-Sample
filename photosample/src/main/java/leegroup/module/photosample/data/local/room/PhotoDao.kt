package leegroup.module.photosample.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import leegroup.module.photosample.data.local.room.entities.PhotoEntity

@Dao
internal interface PhotoDao {

    @Upsert
    suspend fun upsert(users: PhotoEntity)

    @Upsert
    suspend fun upsert(users: List<PhotoEntity>)

    @Query(
        """
    SELECT * FROM PhotoEntity 
    ORDER BY id 
    LIMIT :limit OFFSET :since  
"""
    )
    suspend fun getPhotos(since: Int, limit: Int): List<PhotoEntity>

    @Query(
        """
    SELECT * FROM PhotoEntity 
    WHERE (id IN (:ids) AND id > :since)
    ORDER BY id 
    LIMIT :limit  
"""
    )
    suspend fun getPhotosByIds(since: Int, limit: Int, ids: List<Int>): List<PhotoEntity>
}