package com.example.huertohogar.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

class UserSessionRepository(private val context: Context) {

    private val isLoggedInKey = booleanPreferencesKey("is_logged_in")

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map {
        it[isLoggedInKey] ?: false
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit {
            it[isLoggedInKey] = isLoggedIn
        }
    }
}