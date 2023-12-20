//package com.dicoding.stylemate.detail
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import com.bumptech.glide.Glide
//import com.dicoding.stylemate.R
//import com.dicoding.stylemate.api.ListPotongItem
//import com.dicoding.stylemate.databinding.ActivityDetailBinding
//
//class DetailActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityDetailBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val data = intent.getParcelableExtra<ListPotongItem>(KEY_CONTENT) as ListPotongItem
//        setData(data)
//    }
//
//    private fun setData(content:  ListPotongItem) {
//        Glide.with(this)
//            .load(content.imageUrls)
//            .into(binding.ivSalon)
//        binding.apply {
//            binding.tvNamaSalon.text = content.name
//            binding.tvNilaiRating.text = content.rating
//
//        }
//    }
//
//    companion object {
//        const val KEY_CONTENT = "content"
//    }
//}