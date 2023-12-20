package com.dicoding.stylemate.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.stylemate.R
import com.dicoding.stylemate.api.ListPotongItem
import com.dicoding.stylemate.databinding.ItemContentBinding

class SalonAdapter(val dataSalon: List<ListPotongItem>): RecyclerView.Adapter<SalonAdapter.MyViewHolder>() {
    private lateinit var onMapClickCallback: OnMapClickCallback
    private lateinit var onTelpClickCallback: OnTelpClickCallback

    fun setOnMapClickCallback(onMapClickCallback: OnMapClickCallback) {
        this.onMapClickCallback = onMapClickCallback
    }

    fun setOnTelpClickCallback(onTelpClickCallback: OnTelpClickCallback) {
        this.onTelpClickCallback = onTelpClickCallback
    }

    class MyViewHolder(binding: ItemContentBinding) : RecyclerView.ViewHolder(binding.root){
        val imgSalon = binding.ivItemFotoSalon
        val namaSalon = binding.tvItemNamaSalon
        val ratingSalon = binding.tvItemRatingSalon
        val lokasi = binding.btnMaps
        val telepon = binding.btnTelepon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSalon.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataSalon[position]
        holder.namaSalon.text = data.name
        holder.ratingSalon.text = data.rating

        Glide.with(holder.imgSalon)
            .load(data.imageUrls?.get(0))
            .error(R.drawable.angie)
            .into(holder.imgSalon)

        holder.lokasi.setOnClickListener {
            onMapClickCallback.onItemClicked(dataSalon[position])
        }

        holder.telepon.setOnClickListener{
            onTelpClickCallback.onItemClicked(dataSalon[position])
        }
    }

    interface OnMapClickCallback {
        fun onItemClicked(data: ListPotongItem)
    }

    interface OnTelpClickCallback {
        fun onItemClicked(data: ListPotongItem)
    }

}