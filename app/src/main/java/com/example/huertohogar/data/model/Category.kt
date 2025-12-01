package com.example.huertohogar.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val name: String,
    @SerializedName("imagen") val imageUrl: String? = null
)