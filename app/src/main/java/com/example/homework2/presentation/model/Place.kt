package com.example.homework2.presentation.model

data class Place(
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    val location: String,
    val reactionCount: Int,
    val rate: Int?
)
