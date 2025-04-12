package com.aslansoft.caffesist.classes

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class BinInfo(
    @SerializedName("scheme") val scheme: String,
    @SerializedName("type") val type: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("bank") val bank: Bank
)

data class Bank(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("city") val city: String
)

interface BinListApi {
    @GET("{bin}")
    fun getBinInfo(@Path("bin") bin: String): Call<BinInfo>
}


object RetrofitClient {
    private const val BASE_URL = "https://lookup.binlist.net/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val binListApi: BinListApi = retrofit.create(BinListApi::class.java)
}