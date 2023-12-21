package com.dicoding.stylemate.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.dicoding.stylemate.CameraActivity
import com.dicoding.stylemate.CameraActivity.Companion.CAMERAX_RESULT
import com.dicoding.stylemate.databinding.FragmentScanBinding
import com.dicoding.stylemate.home.HomeViewModel
import com.dicoding.stylemate.recommend.RecommendActivity
import com.dicoding.stylemate.uriToFile

class Scan : Fragment() {

    private lateinit var binding: FragmentScanBinding
    private var currentImageUri: Uri? = null
    private lateinit var viewModel: ScanViewModel
    private var predictWait = true

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScanBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Rekomendasi Gaya Rambut"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ScanViewModel::class.java]

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        viewModel.data.observe(viewLifecycleOwner){
            predictWait = false
            binding.progressBar2.visibility = View.INVISIBLE
            binding.tvResult.text = it.predictionText
            binding.tvResult.visibility = View.VISIBLE
        }

        binding.btnGaleri.setOnClickListener { startGallery() }
        binding.btnCamera.setOnClickListener { startCameraX() }
        binding.btnUpload.setOnClickListener {
            if(currentImageUri != null){
                if(predictWait){
                    Toast.makeText(requireContext(), "Tunggu hasil prediksi keluar", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(requireContext(), RecommendActivity::class.java)
                    intent.putExtra(RecommendActivity.EXTRA_FACE, binding.tvResult.text.toString())
                    startActivity(intent)
                }
            } else {
                Toast.makeText(requireContext(), "Ambil foto terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }


    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
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
        predictWait = true
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")

            viewModel.upload(uriToFile(it, requireContext()))
            binding.progressBar2.visibility = View.VISIBLE
            binding.previewImageView.setImageURI(it)
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

}