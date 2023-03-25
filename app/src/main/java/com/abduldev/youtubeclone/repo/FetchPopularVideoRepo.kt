package com.abduldev.youtubeclone.repo

import com.abduldev.youtubeclone.data.api.YoutubeApi
import com.abduldev.youtubeclone.data.api.models.YoutubeResponse
import retrofit2.Response
import javax.inject.Inject


class FetchPopularVideoRepo
@Inject constructor(private val api: YoutubeApi) {
    suspend fun getPopularVideos():
            Response<YoutubeResponse> {
        return api.fetchVideos()
    }
}