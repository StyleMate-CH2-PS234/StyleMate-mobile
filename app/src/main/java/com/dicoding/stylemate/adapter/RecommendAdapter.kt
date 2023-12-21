package com.dicoding.stylemate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.stylemate.R
import com.dicoding.stylemate.databinding.RecommendContentBinding

class RecommendAdapter(val images: List<String>): RecyclerView.Adapter<RecommendAdapter.ViewHolder>() {

    class ViewHolder(binding: RecommendContentBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivRecommend = binding.ivItemRecommend
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendAdapter.ViewHolder {
        val binding = RecommendContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendAdapter.ViewHolder, position: Int) {
        Glide.with(holder.ivRecommend)
            .load(images[position])
            .error(R.drawable.angie)
            .into(holder.ivRecommend)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}