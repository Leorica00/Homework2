package com.example.homework2.presentation.screen.places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.R
import com.example.homework2.databinding.LocationTypeItemBinding
import com.example.homework2.presentation.model.LocationType

class LocationTypesListAdapter :
    ListAdapter<LocationType, LocationTypesListAdapter.LocationCategoryViewHolder>(
        LocationTypeDiffCallback()
    ) {

    var onItemClick: ((LocationType) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationCategoryViewHolder {
        return LocationCategoryViewHolder(
            LocationTypeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LocationCategoryViewHolder, position: Int) {
        holder.bind()
    }

    inner class LocationCategoryViewHolder(private val binding: LocationTypeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val location = currentList[adapterPosition]
            binding.tvType.text = location.type
            if (location.isChosen) {
                binding.root.setBackgroundResource(R.drawable.selected_type_background)
            } else {
                binding.root.setBackgroundResource(R.drawable.unselected_type_background)
            }
            binding.root.setOnClickListener {
                onItemClick?.invoke(location)
            }
        }
    }

    class LocationTypeDiffCallback : DiffUtil.ItemCallback<LocationType>() {
        override fun areItemsTheSame(oldItem: LocationType, newItem: LocationType): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: LocationType, newItem: LocationType): Boolean {
            return oldItem == newItem
        }
    }
}