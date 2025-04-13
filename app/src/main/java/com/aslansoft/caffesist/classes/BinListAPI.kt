package com.aslansoft.caffesist.classes

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class ApiResponse(
    @SerializedName("result") val result: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: BinData?
)

data class BinData(
    @SerializedName("card") val card: CardInfo?,
    @SerializedName("country") val country: CountryInfo?,
    @SerializedName("bank") val bank: BankInfo?
)

data class CardInfo(
    @SerializedName("scheme") val scheme: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("length") val length: Int?,
    @SerializedName("checkluhn") val checkLuhn: Int?,
    @SerializedName("cvvlen") val cvvLen: Int?
)

data class CountryInfo(
    @SerializedName("name") val name: String?,
    @SerializedName("code") val code: String?,
    @SerializedName("flag") val flag: String?,
    @SerializedName("currency") val currency: String?,
    @SerializedName("currency_code") val currencyCode: String?
)

data class BankInfo(
    @SerializedName("name") val name: String?,
    @SerializedName("website") val website: String?,
    @SerializedName("phone") val phone: String?
)



interface BinTableApi {
    @GET("v1/{bin}")
    fun getBinInfo(
        @Path("bin") bin: String,
        @Query("api_key") apiKey: String
    ): Call<ApiResponse>
}


object RetrofitClient {
    private const val BASE_URL = "https://api.bintable.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val binTableApi: BinTableApi = retrofit.create(BinTableApi::class.java)
}
