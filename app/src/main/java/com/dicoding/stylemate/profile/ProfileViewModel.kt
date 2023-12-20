package com.dicoding.stylemate.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.stylemate.api.ApiConfig
import com.dicoding.stylemate.api.Data
import com.dicoding.stylemate.api.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val isSukses = MutableLiveData<Boolean>()
    val data = MutableLiveData<Data>()
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
                            data.postValue(response.body()!!.data!!)
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
                    isSukses.postValue(false)
                isLoading.postValue(false)
            }

        })

    }
}