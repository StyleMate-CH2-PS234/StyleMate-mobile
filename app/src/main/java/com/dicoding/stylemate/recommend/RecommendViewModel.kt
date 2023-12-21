package com.dicoding.stylemate.recommend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.stylemate.api.ApiConfig
import com.dicoding.stylemate.api.RecommendResponse
import com.dicoding.stylemate.api.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendViewModel:ViewModel() {
    val images = MutableLiveData<List<String>>()
    val isLoading = MutableLiveData<Boolean>()
    val isSukses = MutableLiveData<Boolean>()

    fun getRecommend(face:String){
        val apiService = ApiConfig.getApiService()
        val loginRequest = apiService.getRecommend(face)
        isLoading.postValue(true)
        loginRequest.enqueue(object: Callback<RecommendResponse> {
            override fun onResponse(call: Call<RecommendResponse>, response: Response<RecommendResponse>) {
                isLoading.postValue(false)
                if(response.isSuccessful){
                    if(response.body() != null){
                        if(!response.body()!!.error!!){
                            isSukses.postValue(true)
                            images.postValue(response.body()!!.imageUrls as List<String>?)
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

            override fun onFailure(call: Call<RecommendResponse>, t: Throwable) {
                isLoading.postValue(false)
            }



        })

    }
}