package com.example.homework2.domain.repository

import com.example.homework2.domain.model.GetPlace
import com.example.homework2.domain.util.Resource
import kotlinx.coroutines.flow.Flow

fun interface PlacesRepository {
    suspend fun getPlaces(): Flow<Resource<List<GetPlace>>>
}