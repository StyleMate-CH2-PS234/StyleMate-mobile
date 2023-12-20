package com.dicoding.stylemate.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.stylemate.api.ApiConfig
import com.dicoding.stylemate.api.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val isSukses = MutableLiveData<Boolean>()
    val token = MutableLiveData<String>()
    val refreshToken = MutableLiveData<String>()


    fun login(email: String, password: String){
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
                            token.postValue(response.body()!!.data!!.stsTokenManager!!.accessToken)
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