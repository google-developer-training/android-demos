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
package com.android.samples.donuttracker.coffee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.samples.donuttracker.core.storage.CoffeeDao

class CoffeeViewModelFactory(private val coffeeDao: CoffeeDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoffeeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoffeeListViewModel(coffeeDao) as T
        } else if (modelClass.isAssignableFrom(CoffeeEntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoffeeEntryViewModel(coffeeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
