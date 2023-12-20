package com.dicoding.stylemate.editProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.stylemate.api.ApiConfig
import com.dicoding.stylemate.api.Data
import com.dicoding.stylemate.api.LoginResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditProfileViewModel : ViewModel(){
    val isSukses = MutableLiveData<Boolean>()
    val isSuksesPass = MutableLiveData<Boolean>()
    val data = MutableLiveData<Data>()

    fun changeName(email: String, password: String, name: String){
        val apiService = ApiConfig.getApiService()
        val changeRequest = apiService.changeName(email, password, name)
        changeRequest.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
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
            }

        })
    }

    fun changePassword(email: String, password: String, newPassword: String){
        val apiService = ApiConfig.getApiService()
        val changeRequest = apiService.changePassword(email, password, newPassword)
        changeRequest.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    if(response.body() != null && response.body()?.data != null){
                        if(response.body()!!.success!!){
                            isSuksesPass.postValue(true)
                            data.postValue(response.body()!!.data!!)
                        } else {
                            isSuksesPass.postValue(false)
                        }
                    } else {
                        isSuksesPass.postValue(false)
                    }
                }else {
                    isSuksesPass.postValue(false)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                isSuksesPass.postValue(false)
            }

        })
    }

    fun changePhoto(email: String, password: String, image: File){
        val apiService = ApiConfig.getApiService()
        val requestImage = image.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            image.name,
            requestImage
        )
        val changeRequest = apiService.changePhoto(email, password, multipartBody)
        changeRequest.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
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
            }

        })
    }
}