package com.abduldev.youtubeclone.data.api.models

data class ContentDetails(
    val caption: String,
    val contentRating: ContentRating,
    val definition: String,
    val dimension: String,
    val duration: String,
    val licensedContent: Boolean,
    val projection: String,
    val regionRestriction: RegionRestriction
)