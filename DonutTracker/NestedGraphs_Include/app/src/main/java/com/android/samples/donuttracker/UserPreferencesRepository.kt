/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.samples.donuttracker

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository private constructor(context: Context) {
    private val dataStore: DataStore<Preferences> =
        context.createDataStore(name = "settings")

    object PreferencesKeys {
        val COFFEE_ENABLED = stringPreferencesKey("coffee_enabled")
    }

    enum class Selection {
        DONUT_ONLY, DONUT_AND_COFFEE, NOT_SELECTED
    }

    suspend fun saveCoffeeTrackerSelection(isSelected: Boolean) {

        val selection = when (isSelected) {
            true -> Selection.DONUT_AND_COFFEE.toString()
            false -> Selection.DONUT_ONLY.toString()
        }

        dataStore.edit { preferences ->
            preferences[PreferencesKeys.COFFEE_ENABLED] = selection
        }
    }

    val coffeeTrackerPreferencesFlow: Flow<Selection> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val strValue =
                preferences[PreferencesKeys.COFFEE_ENABLED] ?: Selection.NOT_SELECTED.toString()
            strValue.asEnumOrDefault()
        }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        fun getInstance(context: Context): UserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferencesRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }

    private inline fun <reified T : Enum<T>> String.asEnumOrDefault(): T =
        enumValues<T>().first { it.name.equals(this, ignoreCase = true) }
}
