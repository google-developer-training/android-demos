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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.samples.donuttracker.core.model.Coffee
import com.android.samples.donuttracker.core.model.Donut

/**
 * The underlying database where information about the donuts is stored.
 */
@Database(entities = arrayOf(Donut::class, Coffee::class), version = 1)
abstract class SnackDatabase : RoomDatabase() {

    abstract fun donutDao(): DonutDao

    abstract fun coffeeDao(): CoffeeDao

    companion object {
        @Volatile private var INSTANCE: SnackDatabase? = null

        fun getDatabase(context: Context): SnackDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    SnackDatabase::class.java,
                    "snack_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
