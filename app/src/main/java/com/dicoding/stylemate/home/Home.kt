package com.dicoding.stylemate.home

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.transition.Transition
import com.dicoding.stylemate.R
import com.dicoding.stylemate.adapter.SalonAdapter
import com.dicoding.stylemate.api.ListPotongItem
import com.dicoding.stylemate.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class Home : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var mapView: MapView? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
//    private val viewAdapter: SalonAdapter = SalonAdapter(List<ListPotongItem>)
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.title = "Lokasi Salon Terdekat"

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mapView = binding.mapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync(this)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]



        viewModel.salonlist.observe(viewLifecycleOwner) { salonList ->
            salonList.forEach {
                val pos = LatLng(it.geometry?.location!!.lat!!, it.geometry.location.lng!!)
                val customMarkerIcon = customMarkerIcon(requireContext(), R.drawable.maps_marker)
                mMap.addMarker(
                    MarkerOptions()
                        .position(pos)
                        .title(it.name)
                        .snippet(it.rating.toString())
                        .icon(customMarkerIcon)
                        
                )
            }

            binding.rvSalon.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val adapter = SalonAdapter(salonList)

            adapter.setOnMapClickCallback(object : SalonAdapter.OnMapClickCallback{
                override fun onItemClicked(data: ListPotongItem) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
                    startActivity(intent)
                }
            })

            adapter.setOnTelpClickCallback(object : SalonAdapter.OnTelpClickCallback{
                override fun onItemClicked(data: ListPotongItem) {
                    val intent = Intent(Intent.ACTION_DIAL)
                    val uri = Uri.parse("tel:${data.formattedPhoneNumber}")
                    intent.data = uri
                    startActivity(intent)
                }
            })

            binding.rvSalon.adapter = adapter
        }
    }

    private fun customMarkerIcon(requireContext: Context, mapsMarker: Int): BitmapDescriptor? {
        val drawable = ContextCompat.getDrawable(requireContext, mapsMarker)

        drawable?.let {
            val width = it.intrinsicWidth
            val height = it.intrinsicHeight

            // Set bounds to the drawable
            it.setBounds(0, 0, width, height)

            // Create a Bitmap
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            // Create a Canvas and draw the drawable to the Bitmap
            val canvas = Canvas(bitmap)
            it.draw(canvas)

            // Return the BitmapDescriptor
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }

        return null
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getMyLocation()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }
    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
//            mMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Use the location object to get the user's location
                    if (location != null) {

                        viewModel.getSalon(location.latitude, location.longitude)

                        val myLoc = LatLng(location.latitude, location.longitude)
                        mMap.addMarker(
                            MarkerOptions()
                                .position(myLoc)
                                .title("Lokasi Saya")
                                .icon(BitmapFromVector(requireContext(), R.drawable.ic_my_location))

                        )
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 12f))
                    }
                }

        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(
            context, vectorResId
        )

        // below line is use to set bounds to our vector
        // drawable.
        vectorDrawable!!.setBounds(
            0, 0, vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our
        // bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

