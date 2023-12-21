package com.dicoding.stylemate.recommend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.stylemate.R
import com.dicoding.stylemate.adapter.RecommendAdapter
import com.dicoding.stylemate.databinding.ActivityRecommendBinding

class RecommendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendBinding
    private lateinit var viewModel: RecommendViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecommendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val face = intent.getStringExtra(EXTRA_FACE)!!
//        val face = "Square"

        viewModel = ViewModelProvider(this)[RecommendViewModel::class.java]
        viewModel.getRecommend(face)

        viewModel.images.observe(this){
            val adapter = RecommendAdapter(it)
            binding.rvRecommend.layoutManager = LinearLayoutManager(this)
            binding.rvRecommend.adapter = adapter

        }


    }

    companion object {
        val EXTRA_FACE = "face"
    }
}