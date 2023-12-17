package com.dicoding.stylemate

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.stylemate.databinding.FragmentProfileBinding
import com.dicoding.stylemate.databinding.FragmentScanBinding

class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gunakan binding untuk mengakses elemen UI
        binding.btnEdit.setOnClickListener {
            // Menggunakan Intent untuk memulai Activity baru
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

}