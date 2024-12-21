package com.example.homework2.data.remote.mapper

import com.example.homework2.data.remote.model.PlaceDto
import com.example.homework2.domain.model.GetPlace

fun PlaceDto.toDomain() = GetPlace(
    id = id,
    cover = cover,
    price = price,
    title = title,
    location = location,
    reactionCount = reactionCount,
    rate = rate
)