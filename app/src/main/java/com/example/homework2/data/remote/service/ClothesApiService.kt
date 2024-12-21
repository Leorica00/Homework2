package com.example.homework2.data.remote.service

import com.example.homework2.data.remote.model.PlaceDto
import retrofit2.Response
import retrofit2.http.GET

fun interface PlacesApiService {
    @GET("54696c27-6ee8-466d-95d7-da64921ab614")
    suspend fun getPlaces(): Response<List<PlaceDto>>
}