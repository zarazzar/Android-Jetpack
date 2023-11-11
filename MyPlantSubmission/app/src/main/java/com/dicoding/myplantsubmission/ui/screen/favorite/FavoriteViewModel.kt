package com.dicoding.myplantsubmission.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myplantsubmission.data.Plant
import com.dicoding.myplantsubmission.ui.state.UiState
import com.dicoding.myplantsubmission.utils.PlantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: PlantRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Plant>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<Plant>>>
        get() = _uiState

    fun getAddedFavorite() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getFavoritedPlant()
                .collect { plants ->
                    _uiState.value = UiState.Success(plants)
                }
        }
    }

    fun addToFavorite(plantId: Long) {
        viewModelScope.launch {
            repository.updatePlant(plantId)
        }
    }
}