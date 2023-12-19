package com.dicoding.stylemate.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.stylemate.DetailActivity
import com.dicoding.stylemate.R
import com.dicoding.stylemate.api.ListPotongItem
import com.dicoding.stylemate.databinding.ItemContentBinding
import de.hdodenhof.circleimageview.CircleImageView

class SalonAdapter(val dataSalon: List<ListPotongItem>): RecyclerView.Adapter<SalonAdapter.MyViewHolder>() {
    class MyViewHolder(binding: ItemContentBinding) : RecyclerView.ViewHolder(binding.root){
        val imgSalon = binding.ivItemFotoSalon
        val namaSalon = binding.tvItemNamaSalon

//        fun bind(user: ListPotongItem) {
//            itemView.setOnClickListener{
//                val intent = Intent(itemView.context, DetailActivity::class.java)
//                itemView.context.startActivity(intent)
//
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSalon.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("Salon Adapter", "onBindViewHolder: masuk onbindviewholder")
        val data = dataSalon[position]
        holder.namaSalon.text = data.name
//
        Glide.with(holder.imgSalon)
            .load(data.imageUrls?.get(0))
            .error(R.drawable.angie)
            .into(holder.imgSalon)
//
//        holder.bind(data)
    }

}