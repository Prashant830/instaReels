package com.prashant.musicvideosample.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.prashant.musicvideosample.model.VideosModel

@Dao
interface VideosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(video: VideosModel)

    @Query("DELETE FROM videos_table")
    fun deleteAllVideos()

    @Query("SELECT * FROM videos_table")
    fun getAllVideos(): LiveData<List<VideosModel>>
}