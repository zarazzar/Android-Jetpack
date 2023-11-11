package com.dicoding.myplantsubmission.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.myplantsubmission.ui.screen.detail.DetailViewModel
import com.dicoding.myplantsubmission.ui.screen.favorite.FavoriteViewModel
import com.dicoding.myplantsubmission.ui.screen.home.HomeViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: PlantRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
    }
}