package com.example.huertohogar.repository

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HuertoHogarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HuertoHogarViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}