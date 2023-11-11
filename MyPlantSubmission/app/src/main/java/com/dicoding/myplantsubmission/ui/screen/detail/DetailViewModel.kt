package com.dicoding.myplantsubmission.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myplantsubmission.data.Plant
import com.dicoding.myplantsubmission.ui.state.UiState
import com.dicoding.myplantsubmission.utils.PlantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: PlantRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Plant>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Plant>>
        get() = _uiState

    fun getPlantById(plantId: Long) {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getPlantById(plantId))
    }

    fun addToFavorite(plantId: Long) {
        viewModelScope.launch {
            repository.updatePlant(plantId)
        }
    }
}