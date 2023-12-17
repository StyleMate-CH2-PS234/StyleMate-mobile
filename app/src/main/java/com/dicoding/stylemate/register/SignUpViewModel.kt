package com.dicoding.stylemate.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.stylemate.api.ApiConfig
import com.dicoding.stylemate.api.LoginResponse
import com.dicoding.stylemate.api.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel: ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val isSukses = MutableLiveData<Boolean>()
    val token = MutableLiveData<String>()
    val refreshToken = MutableLiveData<String>()


    fun register(name:String, email: String, password: String){
        val apiService = ApiConfig.getApiService()
        val loginRequest = apiService.register(name, email, password)
        isLoading.postValue(true)
        loginRequest.enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                isLoading.postValue(false)
                if(response.isSuccessful){
                    if(response.body() != null && response.body()?.data != null){
                        if(response.body()!!.success!!){
                            isSukses.postValue(true)
                            token.postValue(response.body()!!.data!!.stsTokenManager!!.accessToken)
                            refreshToken.postValue(response.body()!!.data!!.stsTokenManager!!.refreshToken)
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

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                isLoading.postValue(false)
            }



        })

    }
}