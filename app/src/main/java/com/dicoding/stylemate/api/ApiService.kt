package com.dicoding.stylemate.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("users/login")
    fun login(@Field("email") email: String,
                 @Field("password") password: String,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("users/register")
    fun register(
        @Field("name") name: String,
                @Field("email") email: String,
              @Field("password") password: String,
    ): Call<RegisterResponse>


    @GET("place/search/{latlng}/salon")
    fun getSalon(
        @Path("latlng") latlng: String
    ): Call<SalonResponse>
}

class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://stylemate-api-tvmtgkrwra-et.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}