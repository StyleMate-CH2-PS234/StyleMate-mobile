package com.dicoding.stylemate.api

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    fun login(@Field("email") email: String,
                 @Field("password") password: String,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("auth/register")
    fun register(
        @Field("name") name: String,
                @Field("email") email: String,
              @Field("password") password: String,
    ): Call<RegisterResponse>

    @Multipart
    @POST("upload/image")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<UploadResponse>


    @GET("place/static/{latlng}/salon")
    fun getSalon(
        @Path("latlng") latlng: String
    ): Call<SalonResponse>

    @GET("place/search/{latlng}/salon")
    fun getSalonSearch(
        @Path("latlng") latlng: String
    ): Call<SalonResponse>

    @FormUrlEncoded
    @POST("auth/name")
    fun changeName(
        @Header("email") email : String,
        @Header("password") password: String,
        @Field("name") name: String
    ) : Call<LoginResponse>

    @Multipart
    @POST("auth/photo")
    fun changePhoto(
        @Header("email") email : String,
        @Header("password") password: String,
        @Part image: MultipartBody.Part
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("auth/password")
    fun changePassword(
        @Header("email") email : String,
        @Header("password") password: String,
        @Field("password") newPassword: String
    ) : Call<LoginResponse>


    @GET("model/recommend")
    fun getRecommend(
        @Query("face") face: String
    ) : Call<RecommendResponse>
}

class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://stylemate-api-tvmtgkrwra-et.a.run.app/")
//                .baseUrl("http://192.168.18.223:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}