package com.myapp.revery

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// This class contains all the GET/POST calls made to ReveryAI api.
class ReveryAIClient {
    private val BASE_URL = "https://api.revery.ai/console/v2/"
    private val TAG = "REVERY_CLIENT_LOG: "
    private val publicKey: String = "7047a79ff16b3393b5b2ff4d35ac8b8e"
    private val privateKey: String = "94ef51828c7a0a3a002042d7c32cff8e"
    private val credentials: Map<String, String> = Authentication().getAuthenticationHeader(publicKey, privateKey)
    private val oneTimeCode: String = credentials["one_time_code"] as String
    private val timestamp: String = credentials["timestamp"] as String
    private val gson = Gson()

    /**
     * Retrieves filtered garments from ReveryAI.
     * Endpoint: `get_filtered_garments`
     *
     * Method: GET
     *
     * Parameters:
     * - category: string, `female` or `male` (optional)
     * - gender: string, one of `tops, bottoms, outerwear, allbody` (optional)
     * - page: int, start from 0 (optional, default: 0)
     * - page_size: int, how many products returned per page (optional, default: 10)
     *
     * Return Example:
     * {
     *     "total_page": 1,
     *     "garments": [
     *         {
     *             "gender": "female",
     *             "id": "5752dc908850d70d60fa3567bfc97520_Sts7i62711LW",
     *             "image_urls": {
     *                 "product_image": "https://revery-integration-tools.s3.us-east-2.amazonaws.com/API_website/bottoms.jpeg"
     *             },
     *             "tryon": {
     *                 "category": "bottoms",
     *                 "bottoms_sub_category": "pants",
     *                 "open_outerwear": false
     *             }
     *         }
     *     ],
     *     "success": true
     * }
     */
    suspend fun getFilteredGarments(): FilteredGarmentsResponse{
        try {
            // Create connection to Revery API
            val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ReveryAIService::class.java)

            // Make request to get_filtered_garments endpoint.
            val response = api.getFilteredGarments(publicKey, oneTimeCode, timestamp)

            // Handle response, return filteredGarmentResponse data class instance with response content
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    // Parse response body and transform into data classes.
                    println(TAG + "Raw JSON Response: $responseBody")
                    val garmentResponse = gson.fromJson(responseBody, FilteredGarmentsResponse::class.java)
                    println(TAG + garmentResponse)
                    return garmentResponse
                } else {
                    println( TAG + "Response body is null.")
                }
            } else {
                println(TAG + "API request failed with status code: ${response.code()}")
            }
        } catch (e: Exception) {
            println(TAG + "Error fetching data: ${e.message}")
        }
        return FilteredGarmentsResponse()
    }

    /**
     * Return a specific processed garment
     *
     * Endpoint: `https://api.revery.ai/console/v2/get_garment`
     *
     * Method: GET
     *
     * Parameters:
     * - garment_id: string, unique identifier of the product (only string or letter)
     *
     * Header:
     * - public_key: str
     * - timestamp: str
     * - one_time_code: str
     *
     * Return:
     * {
     *     "garment": {
     *         "gender": "female",
     *         "id": "5752dc908850d70d60fa3567bfc97520_Vm9ME193eH2K",
     *         "image_urls": {
     *             "product_image": "https://revery-integration-tools.s3.us-east-2.amazonaws.com/API_website/tops.jpeg"
     *         },
     *         "tryon": {
     *             "category": "tops",
     *             "open_outerwear": false
     *         }
     *     },
     *     "success": true
     * }
     */
    suspend fun getSpecificGarment(garmentID: String): Garment{
        return Garment()
    }
}