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
package com.android.samples.donuttracker.setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.android.samples.donuttracker.UserPreferencesRepository
import kotlinx.coroutines.launch

class SelectionViewModel (
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    fun saveCoffeeTrackerSelection(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveCoffeeTrackerSelection(enabled)
        }
    }

    fun checkCoffeeTrackerEnabled(): LiveData<UserPreferencesRepository.Selection> {
        return userPreferencesRepository.coffeeTrackerPreferencesFlow.asLiveData()
    }
}

class SelectionViewModelFactory(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SelectionViewModel(userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
