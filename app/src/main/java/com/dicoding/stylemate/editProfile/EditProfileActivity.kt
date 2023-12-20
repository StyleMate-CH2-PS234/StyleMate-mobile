package com.dicoding.stylemate.editProfile

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.stylemate.api.Data
import com.dicoding.stylemate.data.DataPreferences
import com.dicoding.stylemate.databinding.ActivityEditProfileBinding
import com.dicoding.stylemate.uriToFile
import java.io.File

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private var currentImageUri: Uri? = null
    private var nameEdited: String? = null
    private var imageEdited: File? = null
    private lateinit var viewModel: EditProfileViewModel
    private var tempPassword: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataPreferences = DataPreferences(this)

        viewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]
        
        viewModel.isSukses.observe(this){
            if(it){
                Toast.makeText(this, "Perubahan Disimpan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Perubahan Gagal", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isSuksesPass.observe(this){
            if(it){
                dataPreferences.setEmailPass(dataPreferences.getEmail(), tempPassword!!)
                Toast.makeText(this, "Password berhasil diubah", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Password gagal diubah", Toast.LENGTH_SHORT).show()
            }
        }

        val user = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Data>(EXTRA_USER, Data::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Data>(EXTRA_USER)
        }

        if(user != null){
            nameEdited = user.displayName

            Glide.with(binding.ivUbahFoto).load(user.photoURL).into(binding.ivUbahFoto)
            binding.etUbahNama.setText(user.displayName)
            binding.etUbahEmail.isEnabled = false
            binding.etUbahEmail.setText(user.email)
        }



        binding.btnUbahFoto.setOnClickListener { startGallery() }

        binding.btnSaveChanges.setOnClickListener {
//            Log.d("EditProfile", "onCreate: $nameEdited")
            if(nameEdited != binding.etUbahNama.text.toString()){
                viewModel.changeName(dataPreferences.getEmail(), dataPreferences.getPass(), binding.etUbahNama.text.toString())
            }

            if(imageEdited != null){
                viewModel.changePhoto(dataPreferences.getEmail(), dataPreferences.getPass(), imageEdited!!)
            }
            
            if(binding.etPasswordLama.text.toString() != ""){
                if(binding.etPasswordLama.text.toString() == dataPreferences.getPass()){
                    if(binding.etPasswordBaru.text.toString().length > 6){
                        tempPassword = binding.etPasswordBaru.text.toString()
                        viewModel.changePassword(dataPreferences.getEmail(), dataPreferences.getPass(), binding.etPasswordBaru.text.toString())
                    } else {
                        Toast.makeText(this, "Password baru minimal 6 karakter", Toast.LENGTH_SHORT).show()
                    }
                    
                } else {
                    Toast.makeText(this, "Password lama salah", Toast.LENGTH_SHORT).show()
                }
            }

            if(nameEdited == binding.etUbahNama.text.toString() && imageEdited == null && binding.etPasswordLama.text.toString() == ""){
                Toast.makeText(this, "Data tidak ada yang diubah", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            imageEdited = uriToFile(it, this)
            binding.ivUbahFoto.setImageURI(it)
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }

}


