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
import androidx.lifecycle.viewModelScope
import com.android.samples.donuttracker.core.model.Coffee
import com.android.samples.donuttracker.core.storage.CoffeeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This ViewModel is used to access the underlying data and to observe changes to it.
 */
class CoffeeListViewModel(private val coffeeDao: CoffeeDao) : ViewModel() {

    // Users of this ViewModel will observe changes to its coffees list to know when
    // to redisplay those changes
    val coffeeList: LiveData<List<Coffee>> = coffeeDao.getAll()

    fun delete(coffee: Coffee) = viewModelScope.launch(Dispatchers.IO) {
        coffeeDao.delete(coffee)
    }
}
