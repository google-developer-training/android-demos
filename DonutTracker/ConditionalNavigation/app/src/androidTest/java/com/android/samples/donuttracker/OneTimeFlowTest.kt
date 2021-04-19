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

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.samples.donuttracker.donut.DonutList
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OneTimeFlowTest {

    @Test
    fun testFirstRun() {
        val mockNavController = TestNavHostController(ApplicationProvider.getApplicationContext())

        mockNavController.setGraph(R.navigation.nav_graph)

        val scenario = launchFragmentInContainer {
            DonutList().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever{ viewLifecycleOwner ->
                    if (viewLifecycleOwner != null){
                        Navigation.setViewNavController(fragment.requireView(), mockNavController)
                    }
                }
            }
        }

        scenario.onFragment {
            assertThat(mockNavController.currentDestination?.id).isEqualTo(R.id.selectionFragment)
        }
    }

}