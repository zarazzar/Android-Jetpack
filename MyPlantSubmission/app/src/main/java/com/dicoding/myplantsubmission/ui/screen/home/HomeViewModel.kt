package com.dicoding.myplantsubmission.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myplantsubmission.data.Plant
import com.dicoding.myplantsubmission.ui.state.UiState
import com.dicoding.myplantsubmission.utils.PlantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: PlantRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Plant>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<Plant>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getAllPlants() {
        viewModelScope.launch {
            repository.getAllPlants()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { plants ->
                    _uiState.value = UiState.Success(plants)
                }
        }
    }

    fun searchPlant(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            repository.search(_query.value)
                .collect{ plant ->
                    _uiState.value = UiState.Success(plant)
                }
        }
    }
}