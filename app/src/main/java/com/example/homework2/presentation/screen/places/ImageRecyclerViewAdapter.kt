package com.example.homework2.presentation.screen.places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.databinding.ItemImageBinding
import com.example.homework2.presentation.extension.loadImage
import com.example.homework2.presentation.model.Place

class ImageRecyclerViewAdapter : ListAdapter<Place, ImageRecyclerViewAdapter.ImageViewHolder>(
    placeDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind()
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val place = currentList[adapterPosition]
            with(binding) {
                imageViewCover.loadImage(place.cover)
                tvPrice.text = place.price
                tvPlace.text = place.location
                tvTitle.text = place.title
                ratingStars.rating = place.rate?.toFloat() ?: 0.0f
                tvReaction.text = place.reactionCount.toString()
            }
        }
    }

    companion object {
        private val placeDiffCallback = object : DiffUtil.ItemCallback<Place>() {

            override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem == newItem
            }
        }
    }
}