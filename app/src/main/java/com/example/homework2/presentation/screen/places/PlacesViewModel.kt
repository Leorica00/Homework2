package com.example.homework2.presentation.screen.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework2.domain.usecase.GetPlacesUseCase
import com.example.homework2.domain.util.Resource
import com.example.homework2.presentation.mapper.toPresentation
import com.example.homework2.presentation.model.LocationType
import com.example.homework2.presentation.state.PlacesState
import com.example.homework2.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(private val getPlacesUseCase: GetPlacesUseCase) :
    ViewModel() {

    private val _placesStateFlow = MutableStateFlow(PlacesState())
    val placesStateFlow = _placesStateFlow.asStateFlow()

    init {
        getPlaces()
    }

    private fun getPlaces() {
        viewModelScope.launch {
            getPlacesUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _placesStateFlow.update { currentState -> currentState.copy(isLoading = resource.loading) }
                    }

                    is Resource.Success -> {
                        val locationTypes = mutableSetOf(LocationType("All", true))
                        locationTypes.addAll(resource.response.map { LocationType(it.location, false) }.toSet())
                        _placesStateFlow.update { currentState ->
                               currentState.copy(
                                places = resource.response.map { it.toPresentation() },
                                chosenPlaces = resource.response.map { it.toPresentation() },
                                locationTypes = locationTypes
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateErrorMessage(getErrorMessage(resource.error))
                    }
                }
            }
        }
    }

    fun filterPlaces(type: String) {
        if (type == "All") {
            _placesStateFlow.update { currentState ->
                currentState.copy(
                    chosenPlaces = currentState.places,
                    locationTypes = currentState.locationTypes?.map { it.copy(isChosen = (it.type == type)) }
                        ?.toSet()
                )
            }
        } else {
            _placesStateFlow.update { currentState ->
                currentState.copy(
                    chosenPlaces = currentState.places?.filter { it.location == type },
                    locationTypes = currentState.locationTypes?.map { it.copy(isChosen = (it.type == type)) }
                        ?.toSet()

                )
            }
        }

    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _placesStateFlow.update { currentState ->
            currentState.copy(errorMessage = errorMessage)
        }
    }
}