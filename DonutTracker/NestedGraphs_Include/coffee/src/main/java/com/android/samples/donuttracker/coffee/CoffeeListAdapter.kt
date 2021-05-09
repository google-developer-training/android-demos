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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.samples.donuttracker.coffee.databinding.CoffeeItemBinding
import com.android.samples.donuttracker.core.model.Coffee

/**
 * The adapter used by the RecyclerView to display the current list of Coffees
 */
class CoffeeListAdapter(
    private var onEdit: (Coffee) -> Unit,
    private var onDelete: (Coffee) -> Unit
) : ListAdapter<Coffee, CoffeeListAdapter.CoffeeListViewHolder>(CoffeeDiffCallback()) {

    class CoffeeListViewHolder(
        private val binding: CoffeeItemBinding,
        private var onEdit: (Coffee) -> Unit,
        private var onDelete: (Coffee) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var coffeeId: Long = -1
        private var nameView = binding.name
        private var description = binding.description
        private var thumbnail = binding.thumbnail
        private var rating = binding.rating
        private var coffee: Coffee? = null

        fun bind(coffee: Coffee) {
            coffeeId = coffee.id
            nameView.text = coffee.name
            description.text = coffee.description
            rating.text = coffee.rating.toString()
            thumbnail.setImageResource(R.drawable.coffee_cup)
            this.coffee = coffee
            binding.deleteButton.setOnClickListener {
                onDelete(coffee)
            }
            binding.root.setOnClickListener {
                onEdit(coffee)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  CoffeeListViewHolder(
        CoffeeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onEdit,
        onDelete
    )

    override fun onBindViewHolder(holder: CoffeeListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CoffeeDiffCallback : DiffUtil.ItemCallback<Coffee>() {
    override fun areItemsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
        return oldItem == newItem
    }
}
