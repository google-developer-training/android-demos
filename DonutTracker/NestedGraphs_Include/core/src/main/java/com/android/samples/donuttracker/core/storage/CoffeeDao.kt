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
package com.android.samples.donuttracker.core.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.android.samples.donuttracker.core.model.Coffee

@Dao
interface CoffeeDao {
    @Query("SELECT * FROM coffee")
    fun getAll(): LiveData<List<Coffee>>

    @Query("SELECT * FROM coffee WHERE id = :id")
    suspend fun get(id: Long): Coffee

    @Insert
    suspend fun insert(coffee: Coffee): Long

    @Delete
    suspend fun delete(coffee: Coffee)

    @Update
    suspend fun update(coffee: Coffee)
}
