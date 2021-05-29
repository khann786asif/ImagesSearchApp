package com.example.imagesearchapp.screens.overview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.UnsplashPhotoItemBinding
import com.example.imagesearchapp.network.UnsplashResponse

class OverviewAdapter(val listener: OnClickListener) :
    RecyclerView.Adapter<OverviewAdapter.ViewHolder>() {
    private var response: ArrayList<UnsplashResponse.UnsplashResults>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UnsplashPhotoItemBinding.inflate(LayoutInflater.from(
            parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = response?.get(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return response?.size ?: 0
    }

    fun submitData(response: ArrayList<UnsplashResponse.UnsplashResults>){
        this.response = response
    }

    inner class ViewHolder(private val binding: UnsplashPhotoItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(currentItem: UnsplashResponse.UnsplashResults){
            Glide
                .with(itemView)
                .load(currentItem.urls.regular)
                .centerCrop()
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_error)
                .into(binding.imageView)

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onClick(currentItem)
                }
            }
        }
    }

    interface OnClickListener{
        fun onClick(result: UnsplashResponse.UnsplashResults)
    }
}