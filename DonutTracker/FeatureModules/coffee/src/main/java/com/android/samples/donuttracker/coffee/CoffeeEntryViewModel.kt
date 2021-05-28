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

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.android.samples.donuttracker.core.model.Coffee
import com.android.samples.donuttracker.core.storage.CoffeeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoffeeEntryViewModel(private val coffeeDao: CoffeeDao) : ViewModel() {

    private var coffeeLiveData: LiveData<Coffee>? = null

    fun get(id: Long): LiveData<Coffee> {
        return coffeeLiveData ?: liveData {
            emit(coffeeDao.get(id))
        }.also {
            coffeeLiveData = it
        }
    }

    fun addData(
        id: Long,
        name: String,
        description: String,
        rating: Int,
        setupNotification: (Long) -> Unit
    ) {
        val coffee = Coffee(id, name, description, rating)

        CoroutineScope(Dispatchers.IO).launch {
            var actualId = id

            if (id > 0) {
                update(coffee)
            } else {
                actualId = insert(coffee)
            }

            setupNotification(actualId)
        }
    }

    private suspend fun insert(donut: Coffee) = coffeeDao.insert(donut)

    private fun update(donut: Coffee) = viewModelScope.launch(Dispatchers.IO) {
        coffeeDao.update(donut)
    }
}
