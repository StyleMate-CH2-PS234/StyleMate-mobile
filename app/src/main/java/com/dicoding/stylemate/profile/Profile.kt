package com.dicoding.stylemate.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.stylemate.EditProfileActivity
import com.dicoding.stylemate.data.DataPreferences
import com.dicoding.stylemate.databinding.FragmentProfileBinding
import com.dicoding.stylemate.login.LoginActivity

class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Profil"
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataPreferences = DataPreferences(requireContext())

        // Gunakan binding untuk mengakses elemen UI
        binding.btnEdit.setOnClickListener {
            // Menggunakan Intent untuk memulai Activity baru
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            dataPreferences.setToken("")
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}