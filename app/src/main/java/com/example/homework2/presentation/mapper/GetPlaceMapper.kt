package com.example.homework2.presentation.mapper

import com.example.homework2.domain.model.GetPlace
import com.example.homework2.presentation.model.Place

fun GetPlace.toPresentation() = Place(
    id = id,
    cover = cover,
    price = price,
    title = title,
    location = location,
    reactionCount = reactionCount,
    rate = rate
)