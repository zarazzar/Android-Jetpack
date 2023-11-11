package com.dicoding.myplantsubmission.utils

object Injection {
    fun provideRepository(): PlantRepository {
        return PlantRepository.getInstance()
    }
}