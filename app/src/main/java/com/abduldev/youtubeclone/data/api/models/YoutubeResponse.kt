package com.abduldev.youtubeclone.data.api.models

data class YoutubeResponse(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo
)