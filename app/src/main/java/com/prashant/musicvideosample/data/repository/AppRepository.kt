package com.prashant.musicvideosample.data.repository

import androidx.lifecycle.LiveData
import com.prashant.musicvideosample.model.VideosModel

interface AppRepository {

    suspend fun insertVideo(video : VideosModel)

    suspend fun deleteVideos()

    suspend fun getAllVideos() : LiveData<List<VideosModel>>
}