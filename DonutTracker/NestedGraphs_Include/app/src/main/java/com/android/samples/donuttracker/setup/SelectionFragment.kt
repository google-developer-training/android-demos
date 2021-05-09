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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.android.samples.donuttracker.UserPreferencesRepository
import com.android.samples.donuttracker.databinding.FragmentSelectionBinding

/**
 * This Fragment enables/disables coffee tracking feature.
 */
class SelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentSelectionBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentSelectionBinding.bind(view)

        val selectionViewModel: SelectionViewModel by viewModels {
            SelectionViewModelFactory(UserPreferencesRepository.getInstance(requireContext()))
        }

        selectionViewModel.checkCoffeeTrackerEnabled().observe(viewLifecycleOwner) { selection ->
            if (selection == UserPreferencesRepository.Selection.DONUT_AND_COFFEE) {
                binding.checkBox.isChecked = true
            }
        }

        binding.button.setOnClickListener { button ->
            val coffeeSelected = binding.checkBox.isChecked
            selectionViewModel.saveCoffeeTrackerSelection(coffeeSelected)

            button.findNavController().navigate(
                SelectionFragmentDirections.actionSelectionFragmentToDonutList()
            )
        }
    }
}
