package com.example.homework2.presentation.state

import com.example.homework2.presentation.model.Place
import com.example.homework2.presentation.model.LocationType

data class PlacesState(
    val isLoading: Boolean = false,
    val places: List<Place>? = null,
    val chosenPlaces: List<Place>? = null,
    val locationTypes: Set<LocationType>? = null,
    val errorMessage: Int? = null
)