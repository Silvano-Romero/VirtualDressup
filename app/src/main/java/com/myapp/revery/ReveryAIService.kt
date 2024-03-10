package com.myapp.revery
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.QueryMap

// Interface that defines how Retrofit talks to Revery server using HTTP requests.
interface ReveryAIService {
    @GET("get_filtered_garments")
    suspend fun getFilteredGarments(@Header("public_key") publicKey: String,
                                    @Header("one_time_code") oneTimeCode: String,
                                    @Header("timestamp") timeStamp: String,
                                    @QueryMap queryParams: Map<String, String>): Response<JsonElement>
    @GET("get_garment")
    suspend fun getSpecificGarment(@Header("public_key") publicKey: String,
                                   @Header("one_time_code") oneTimeCode: String,
                                   @Header("timestamp") timeStamp: String,
                                   @Query("garment_id") garmentId: String): Response<JsonElement>
    @POST("process_new_garment")
    suspend fun uploadSpecificGarment(@Header("public_key") publicKey: String,
                                      @Header("one_time_code") oneTimeCode: String,
                                      @Header("timestamp") timeStamp: String,
                                      @Body request: GarmentToUpload): Response<JsonElement>
    @PUT("delete_garment")
    suspend fun deleteGarment(@Header("public_key") publicKey: String,
                              @Header("one_time_code") oneTimeCode: String,
                              @Header("timestamp") timeStamp: String,
                              @Body request: GarmentToDelete): Response<JsonElement>
    @GET("get_selected_models")
    suspend fun getModelsList(@Header("public_key") publicKey: String,
                              @Header("one_time_code") oneTimeCode: String,
                              @Header("timestamp") timeStamp: String,
                              @Query("gender") gender: String ): Response<JsonElement>


    @PUT("delete_model")
    suspend fun deleteModel(@Header("public_key") publicKey: String,
                            @Header("one_time_code") oneTimeCode: String,
                            @Header("timestamp") timeStamp: String,
                            @Body request: GarmentToDelete): Response<JsonElement>

    @POST("request_tryon")
    suspend fun requestTryOn(@Header("public_key") publicKey: String,
                             @Header("one_time_code") oneTimeCode: String,
                             @Header("timestamp") timeStamp: String,
                             @Body request: TryOnRequest): Response<JsonElement>
    @PUT("modify_garment")
    suspend fun modifyGarment(@Header("public_key") publicKey: String,
                              @Header("one_time_code") oneTimeCode: String,
                              @Header("timestamp") timeStamp: String,
                              @Body request:GarmentToModify): Response<JsonElement>

    @POST("process_new_model")
    suspend fun uploadSpecificModel(@Header("public_key") publicKey: String,
                                      @Header("one_time_code") oneTimeCode: String,
                                      @Header("timestamp") timeStamp: String,
                                      @Body request: ModelToUpload): Response<JsonElement>
}

