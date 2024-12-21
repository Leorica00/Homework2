package com.example.homework2.data.remote.model

import com.squareup.moshi.Json

data class PlaceDto (
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    val location: String,
    @Json(name = "reaction_count")
    val reactionCount: Int,
    val rate: Int?
)