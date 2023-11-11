package com.dicoding.myplantsubmission.data

data class Plant (
    val id: Long,
    val imageUrl: String,
    val name: String,
    val description: String,
    val favorited: Boolean = false,
)