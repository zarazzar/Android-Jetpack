package com.dicoding.myplantsubmission.utils

import com.dicoding.myplantsubmission.data.Plant
import com.dicoding.myplantsubmission.data.PlantsDataSource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlantRepository {

    private val plants = mutableListOf<Plant>()

    init {
        if (plants.isEmpty()) {
            PlantsDataSource.dummyPlants.forEach {
                plants.add(it)
            }
        }
    }

    fun getAllPlants(): Flow<List<Plant>> {
        return flowOf(plants)
    }

    fun getPlantById(plantId: Long): Plant {
        return plants.first {
            it.id == plantId
        }
    }

    fun updatePlant(plantId: Long): Flow<Boolean> {
        val index = plants.indexOfFirst { it.id == plantId }
        val result = if (index >= 0) {
            val addPlant = plants[index]
            plants[index] = addPlant.copy(favorited = !addPlant.favorited)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getFavoritedPlant(): Flow<List<Plant>> {
        return getAllPlants()
            .map { addedPlants ->
                addedPlants.filter { plant ->
                    plant.favorited
                }
            }
    }

    fun search(query: String): Flow<List<Plant>> {
        return flowOf(
            plants.filter {
                it.name.contains(query, ignoreCase = true)
            }
        )
    }

    companion object {
        @Volatile
        private var instance: PlantRepository? = null

        fun getInstance(): PlantRepository =
            instance ?: synchronized(this) {
                PlantRepository().apply {
                    instance = this
                }
            }
    }
}