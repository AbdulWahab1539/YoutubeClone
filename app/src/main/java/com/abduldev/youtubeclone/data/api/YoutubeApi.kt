package com.abduldev.youtubeclone.data.api


import com.abduldev.youtubeclone.data.api.models.YoutubeResponse
import com.abduldev.youtubeclone.utils.Constants.Companion.API_KEY
import com.abduldev.youtubeclone.utils.Constants.Companion.DETAILS
import com.abduldev.youtubeclone.utils.Constants.Companion.MOST_POPULAR
import com.abduldev.youtubeclone.utils.Constants.Companion.REGION_CODE
import com.abduldev.youtubeclone.utils.Constants.Companion.SNIPPET
import com.abduldev.youtubeclone.utils.Constants.Companion.STATISTICS
import com.abduldev.youtubeclone.utils.Constants.Companion.VIDEO_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {

    @GET(VIDEO_ENDPOINT)
    suspend fun fetchVideos(
        @Query("part") part: String = "$SNIPPET,$DETAILS$STATISTICS",
        @Query("chart") chart: String = MOST_POPULAR,
        @Query("regionCode") regionCode: String = REGION_CODE,
        @Query("key") key: String = API_KEY,
    ): Response<YoutubeResponse>
}