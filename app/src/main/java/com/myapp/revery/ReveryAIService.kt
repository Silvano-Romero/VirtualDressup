package com.myapp.revery
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

// Interface that defines how Retrofit talks to Revery server using HTTP requests.
interface ReveryAIService {
    @GET("get_filtered_garments")
    suspend fun getFilteredGarments(@Header("public_key") publicKey: String,
                                    @Header("one_time_code") oneTimeCode: String,
                                    @Header("timestamp") timeStamp: String): Response<JsonElement>
    @GET("get_garment")
    suspend fun getSpecificGarment(@Header("public_key") publicKey: String,
                                    @Header("one_time_code") oneTimeCode: String,
                                    @Header("timestamp") timeStamp: String): Response<JsonElement>
}