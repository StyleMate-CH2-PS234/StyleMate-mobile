package com.dicoding.stylemate.scan

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.stylemate.api.ApiConfig
import com.dicoding.stylemate.api.RegisterResponse
import com.dicoding.stylemate.api.UploadResponse
import com.dicoding.stylemate.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ScanViewModel:ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val isSukses = MutableLiveData<Boolean>()
    val data = MutableLiveData<UploadResponse>()

    fun upload(image: File){
        val apiService = ApiConfig.getApiService()
        val requestImage = image.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            image.name,
            requestImage
        )
        val loginRequest = apiService.uploadImage(multipartBody)
        isLoading.postValue(true)
        loginRequest.enqueue(object: Callback<UploadResponse> {
            override fun onResponse(call: Call<UploadResponse>, response: Response<UploadResponse>) {
                isLoading.postValue(false)
                if(response.isSuccessful){
                    if(response.body() != null){

                            isSukses.postValue(true)
                        data.postValue(response.body())


                    } else {
                        isSukses.postValue(false)
                    }
                }else {
                    isSukses.postValue(false)
                }
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                isLoading.postValue(false)
            }



        })

    }
}