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

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.samples.donuttracker.coffee.databinding.CoffeeEntryDialogBinding
import com.android.samples.donuttracker.core.model.Coffee
import com.android.samples.donuttracker.core.storage.SnackDatabase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * This dialog allows the user to enter information about a cup of coffee, either creating a new
 * entry or updating an existing one.
 */
class CoffeeEntryDialogFragment : BottomSheetDialogFragment() {

    private enum class EditingState {
        NEW_COFFEE,
        EXISTING_COFFEE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return CoffeeEntryDialogBinding.inflate(inflater, container, false).root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val coffeeDao = SnackDatabase.getDatabase(requireContext()).coffeeDao()

        val coffeeEntryViewModel: CoffeeEntryViewModel by viewModels {
            CoffeeViewModelFactory(coffeeDao)
        }

        val binding = CoffeeEntryDialogBinding.bind(view)

        var coffee: Coffee? = null
        val args: CoffeeEntryDialogFragmentArgs by navArgs()
        val editingState =
            if (args.itemId > 0) {
                EditingState.EXISTING_COFFEE
            } else {
                EditingState.NEW_COFFEE
            }

        // If we arrived here with an itemId of >= 0, then we are editing an existing item
        if (editingState == EditingState.EXISTING_COFFEE) {
            // Request to edit an existing item, whose id was passed in as an argument.
            // Retrieve that item and populate the UI with its details
            coffeeEntryViewModel.get(args.itemId).observe(viewLifecycleOwner) { coffeeItem ->
                binding.apply {
                    name.setText(coffeeItem.name)
                    description.setText(coffeeItem.description)
                    ratingBar.rating = coffeeItem.rating.toFloat()
                }

                coffee = coffeeItem
            }
        }

        // When the user clicks the Done button, use the data here to either update
        // an existing item or create a new one
        binding.doneButton.setOnClickListener {
            // Grab these now since the Fragment may go away before the setupNotification
            // lambda below is called
            val context = requireContext().applicationContext
            val navController = findNavController()

            coffeeEntryViewModel.addData(
                coffee?.id ?: 0,
                binding.name.text.toString(),
                binding.description.text.toString(),
                binding.ratingBar.rating.toInt()
            ) { actualId ->
                val arg = CoffeeEntryDialogFragmentArgs(actualId).toBundle()
                val pendingIntent = navController
                    .createDeepLink()
                    .setDestination(R.id.coffeeEntryDialogFragment)
                    .setArguments(arg)
                    .createPendingIntent()

            }
            dismiss()
        }

        // User clicked the Cancel button; just exit the dialog without saving the data
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
}
