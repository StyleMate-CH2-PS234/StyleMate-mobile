package com.dicoding.stylemate.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.stylemate.api.ApiConfig
import com.dicoding.stylemate.api.Data
import com.dicoding.stylemate.api.ListPotongItem
import com.dicoding.stylemate.api.LoginResponse
import com.dicoding.stylemate.api.SalonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class HomeViewModel: ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val isSukses = MutableLiveData<Boolean>()
    val salonlist = MutableLiveData<List<ListPotongItem>>()
    val dataUser = MutableLiveData<Data>()

    fun getSalon(lat : Double, lng: Double){
        val latng: String = "${lat.toString()},${lng.toString()}"
        isLoading.postValue(true)
        val apiService = ApiConfig.getApiService()
        val salonRequest = apiService.getSalon(latng)
            .enqueue(object: Callback<SalonResponse>{
            override fun onResponse(call: Call<SalonResponse>, response: Response<SalonResponse>) {
                isLoading.postValue(false)
                if (response.isSuccessful){
                    if (response.body()!!.listPotong != arrayOf<ListPotongItem>()){
                            isSukses.postValue(true)
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


    fun getSalonSearch(lat : Double, lng: Double){
        val latng: String = "${lat.toString()},${lng.toString()}"
        isLoading.postValue(true)
        val apiService = ApiConfig.getApiService()
        val salonRequest = apiService.getSalonSearch(latng)
            .enqueue(object: Callback<SalonResponse>{
                override fun onResponse(call: Call<SalonResponse>, response: Response<SalonResponse>) {
                    isLoading.postValue(false)
                    if (response.isSuccessful){
                        if (!response.body()!!.listPotong!!.isEmpty()){
                            isSukses.postValue(true)
                            salonlist.postValue(response.body()!!.listPotong as List<ListPotongItem>?)
                        }else{
                            getSalon(lat, lng)
                        }
                    } else{
                        getSalon(lat, lng)
                    }
                }


                override fun onFailure(call: Call<SalonResponse>, t: Throwable) {
                    isLoading.value = false
                    isSukses.postValue(false)
                    Log.e(ContentValues.TAG,"Error:${t.message.toString()}")
                    getSalon(lat, lng)
                }

            })

    }

    fun getUser(email: String, password: String){
        val apiService = ApiConfig.getApiService()
        val loginRequest = apiService.login(email, password)
        isLoading.postValue(true)
        loginRequest.enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                isLoading.postValue(false)
                if(response.isSuccessful){
                    if(response.body() != null && response.body()?.data != null){
                        if(response.body()!!.success!!){
                            isSukses.postValue(true)
                            dataUser.postValue(response.body()!!.data)
                        } else {
                            isSukses.postValue(false)
                        }

                    } else {
                        isSukses.postValue(false)
                    }
                }else {
                    isSukses.postValue(false)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                isLoading.postValue(false)
            }

        })

    }
}