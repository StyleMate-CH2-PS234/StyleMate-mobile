package com.dicoding.stylemate.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.stylemate.api.ApiConfig
import com.dicoding.stylemate.api.ListPotongItem
import com.dicoding.stylemate.api.SalonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class HomeViewModel: ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val isSukses = MutableLiveData<Boolean>()
    val salonlist = MutableLiveData<List<ListPotongItem>>()

    fun getSalon(lat : Double, lng: Double){
        val latng: String = "${lat.toString()},${lng.toString()}"
        isLoading.postValue(true)
        val apiService = ApiConfig.getApiService()
        val salonRequest = apiService.getSalon(latng)
            .enqueue(object: Callback<SalonResponse>{
            override fun onResponse(call: Call<SalonResponse>, response: Response<SalonResponse>) {
                isLoading.postValue(false)
                if (response.isSuccessful){
                    if (response.body() != null){
                            isSukses.postValue(true)
//                        Log.d("HomeViewModel", "onResponse: ${response.body()}")
                            salonlist.postValue(response.body()!!.listPotong as List<ListPotongItem>?)
                    }else{
                        throw HttpException(response)
                    }
                } else{
                    throw HttpException(response)
                }
            }


                override fun onFailure(call: Call<SalonResponse>, t: Throwable) {
                    isLoading.value = false
                    isSukses.postValue(false)
                    Log.e(ContentValues.TAG,"Error:${t.message.toString()}")
                }

            })

    }
}