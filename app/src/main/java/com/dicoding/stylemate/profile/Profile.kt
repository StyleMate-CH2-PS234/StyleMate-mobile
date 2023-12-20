package com.dicoding.stylemate.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.stylemate.editProfile.EditProfileActivity
import com.dicoding.stylemate.data.DataPreferences
import com.dicoding.stylemate.databinding.FragmentProfileBinding
import com.dicoding.stylemate.login.LoginActivity

class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

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

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        val dataPreferences = DataPreferences(requireContext())

        viewModel.getUser(dataPreferences.getEmail(), dataPreferences.getPass())
        viewModel.data.observe(viewLifecycleOwner){
            binding.imageView2.visibility = View.VISIBLE
            binding.tvNamaProfile.visibility = View.VISIBLE
            binding.tvEmailProfile.visibility = View.VISIBLE

            Glide.with(binding.imageView2).load(it.photoURL).into(binding.imageView2)
            binding.tvNamaProfile.text = it.displayName
            binding.tvEmailProfile.text = it.email
            // Gunakan binding untuk mengakses elemen UI
            binding.btnEdit.setOnClickListener {view->
                // Menggunakan Intent untuk memulai Activity baru
                val intent : Intent = Intent(activity, EditProfileActivity::class.java)
                intent.putExtra(EditProfileActivity.EXTRA_USER, it)
                startActivity(intent)
            }
        }

        binding.btnLogout.setOnClickListener {
            dataPreferences.setToken("")
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}