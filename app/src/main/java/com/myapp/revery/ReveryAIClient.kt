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
    private val credentials: Map<String, String> =
        Authentication().getAuthenticationHeader(publicKey, privateKey)
    private val oneTimeCode: String = credentials["one_time_code"] as String
    private val timestamp: String = credentials["timestamp"] as String
    private val gson = Gson()

    // Create connection to Revery API
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ReveryAIService::class.java)

    /**
     * Retrieves filtered garments from ReveryAI.
     * Endpoint: `get_filtered_garments`
     *
     * Method: GET
     *
     * Parameters:
     * - gender: string, `female` or `male` (optional)
     * - category: string, one of `tops, bottoms, outerwear, allbody` (optional)
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
    suspend fun getFilteredGarments(
        gender: String? = null, category: String? = null,
        page: Int = 0, pageSize: Int = 10
    ): FilteredGarmentsResponse {
        try {
            // Handle optional parameters
            val queryParams = mutableMapOf<String, String>()
            if (category != null) queryParams["category"] = category
            if (gender != null) queryParams["gender"] = gender
            queryParams["page"] = page.toString()
            queryParams["page_size"] = pageSize.toString()

            // Make request to get_filtered_garments endpoint.
            val response = api.getFilteredGarments(publicKey, oneTimeCode, timestamp, queryParams)

            // Handle response, return filteredGarmentResponse data class instance with response content
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    // Parse response body and transform into data classes.
                    println(TAG + "Raw JSON Response: $responseBody")
                    val garmentResponse =
                        gson.fromJson(responseBody, FilteredGarmentsResponse::class.java)
                    println(TAG + garmentResponse)
                    return garmentResponse
                } else {
                    println(TAG + "Response body is null.")
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
    suspend fun getGarment(garmentID: String): Garment {
        try {
            // Make request to get_specific_garment endpoint.
            val response = api.getSpecificGarment(publicKey, oneTimeCode, timestamp, garmentID)

            // Handle response, return Garment data class instance with response content
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    // Parse response body and transform into data classes.
                    println(TAG + "Raw JSON Response: $responseBody")
                    val garment = gson.fromJson(responseBody, Garment::class.java)
                    println(TAG + garment)
                    return garment
                } else {
                    println(TAG + "Response body is null.")
                }
            } else {
                println(TAG + "API request failed with status code: ${response.code()}")
            }

        } catch (e: Exception) {
            println(TAG + "Error fetching data: ${e.message}")
        }
        return Garment()
    }

    /**
     * Add a garment to the catalog.
     *
     * Endpoint: https://api.revery.ai/console/v2/process_new_garment
     * Method: POST
     *
     * JSON Parameters (body):
     * - category: string, one of tops, bottoms, outerwear, allbody
     * - bottoms_sub_category: string, if category is bottoms you must specify the type of bottom from one of pants, shorts, skirts
     * - gender: string, one of female or male
     * - garment_img_url: string, url to the ghost mannequin image
     * - brand: string, (optional)
     * - url: string, garment page url (optional)
     *
     * Sample JSON Body:
     * {
     *     "category": "tops",
     *     "gender": "male",
     *     "garment_img_url": "https://revery-integration-tools.s3.us-east-2.amazonaws.com/API_website/tops.jpeg"
     * }
     *
     * Header:
     * - public_key: str
     * - timestamp: str
     * - one_time_code: str
     *
     * Return:
     * {
     *     "garment_id": "5752dc908850d70d60fa3567bfc97520_Vm9ME193eH2K",
     *     "success": true/false
     * }
     */
    suspend fun uploadGarment(garment: GarmentToUpload): String {
        var garmentId = ""

        try {
            // Make request to process_new_garment endpoint.
            val response = api.uploadSpecificGarment(publicKey, oneTimeCode, timestamp, garment)

            // Handle response, return garmentId returned from revery
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    println(TAG + "Raw JSON Response: $responseBody")
                    garmentId = responseBody.asJsonObject.get("garment_id").asString

                    return garmentId
                } else {
                    println(TAG + "Response body is null.")
                }
            } else {
                println(TAG + "API request failed with status code: ${response.code()}")
            }
        } catch (e: Exception) {
            println(TAG + "Error fetching data: ${e.message}")
        }

        return garmentId
    }

    /**
     * Delete a garment from the catalog.
     *
     * Endpoint: https://api.revery.ai/console/v2/delete_model
     * Method: PUT
     *
     * JSON Parameters (body):
     * - model_id: string, unique identifier of the model (only string or letter)
     *   {
     *       "garment_id": "5752dc908850d70d60fa3567bfc97520_Vm9ME193eH2K"
     *   }
     *
     * Header:
     * - public_key: str
     * - timestamp: str
     * - one_time_code: str
     *
     * Return:
     * {
     *     "garment_id": "5752dc908850d70d60fa3567bfc97520_Vm9ME193eH2K",
     *     "success": true/false
     * }
     */
    suspend fun garmentToDelete(garment: GarmentToDelete): String {
        var garmentId = ""

        try {
            // Make request to delete_model endpoint.
            val response = api.deleteGarment(publicKey, oneTimeCode, timestamp, garment)

            // Handle response, return garmentId returned from revery
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    println(TAG + "Raw JSON Response: $responseBody")
                    garmentId = responseBody.asJsonObject.get("garment_id").asString

                    return garmentId
                } else {
                    println(TAG + "Response body is null.")
                }
            } else {
                println(TAG + "API request failed with status code: ${response.code()}")
            }
        } catch (e: Exception) {
            println(TAG + "Error fetching data: ${e.message}")
        }

        return garmentId
    }

    /**
     * Add a model (or/and the shoes that the model is wearing) to the catalog.
     *
     * Endpoint: https://api.revery.ai/console/v2/process_new_model
     * Method: POST
     *
     * JSON Parameters (body):
     * - gender: string, one of female or male
     * - model_img_url: string, url to the ghost mannequin image
     * - standardized: bool, If set to false, the model will resume its original foot pose. If set to true, we will force the model to be straight (helpful to keep a consistent experience.
     *   {
     *       "gender": "female",
     *       "model_img_url": "https://humanaigc-outfitanyone.hf.space/--replicas/ppht9/file=/tmp/gradio/28dbd2deba1e160bfadffbc3675ba4dcac20ca58/Eva_0.png",
     *       "standardized": false
     *   }
     *
     * Header:
     * - public_key: str
     * - timestamp: str
     * - one_time_code: str
     *
     * Return:
     * {
     *     "model_id": "5752dc908850d70d60fa3567bfc97520_Vm9ME193eH2K",
     *     "success": true/false
     * }
     *
     * Maximum Rate limits:
     * 50 uploads/min, 1000 uploads/day
     */
    suspend fun getModels(gender: String): Models {
        try {
            // Make request to get_selected_models endpoint.
            val response = api.getModelsList(publicKey, oneTimeCode, timestamp, gender)

            // Handle response, return Models returned from revery
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    println(TAG + "Raw JSON Response: $responseBody")
                    val models: Models = gson.fromJson(responseBody, Models::class.java)
                    println(TAG + models)

                    return models
                } else {
                    println(TAG + "Response body is null.")
                }
            } else {
                println(TAG + "API request failed with status code: ${response.code()}")
            }
        } catch (e: Exception) {
            println(TAG + "Error fetching data: ${e.message}")
        }

        return Models()
    }

    suspend fun deleteModel(modelId: String): String {
        var modelIdToDelete = ""

        try {
            // Create GarmentToDelete object using modelId
            val garmentToDelete = GarmentToDelete(modelId)

            // Make request to delete_model endpoint.
            val response = api.deleteModel(publicKey, oneTimeCode, timestamp, garmentToDelete)

            // Handle response, return modelId returned from revery
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    println(TAG + "Raw JSON Response: $responseBody")
                    modelIdToDelete = responseBody.asJsonObject.get("model_id").asString

                    return modelIdToDelete
                } else {
                    println(TAG + "Response body is null.")
                }
            } else {
                println(TAG + "API request failed with status code: ${response.code()}")
            }
        } catch (e: Exception) {
            println(TAG + "Error fetching data: ${e.message}")
        }

        return modelIdToDelete
    }

    suspend fun requestTryOn(garments: Map<String, String>, modelId: String, shoesId: String?, background: String = "white", tuckIn: Boolean = false): TryOnResponse {
        try {
            // Make the request body
            val requestBody = TryOnRequest(garments, modelId, shoesId, background, tuckIn)

            // Make request to request_tryon endpoint
            val response = api.requestTryOn(publicKey, oneTimeCode, timestamp, requestBody)

            // Handle response
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    println(TAG + "Raw JSON Response: $responseBody")
                    val tryOnResponse = gson.fromJson(responseBody, TryOnResponse::class.java)
                    return tryOnResponse
                } else {
                    println(TAG + "Response body is null.")
                }
            } else {
                println(TAG + "API request failed with status code: ${response.code()}")
            }
        } catch (e: Exception) {
            println(TAG + "Error fetching data: ${e.message}")
        }
        // Return an empty response or handle errors appropriately
        return TryOnResponse() // Return a default empty response or handle errors appropriately
    }

    // Erwin Pan worked on bottom two methods
    suspend fun uploadModel(model: ModelToUpload): String {
        var modelId = ""

        try {
            // Make request to process_new_model endpoint.
            val response = api.uploadSpecificModel(publicKey, oneTimeCode, timestamp, model)

            // Handle response, return garmentId returned from revery
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    println(TAG + "Raw JSON Response: $responseBody")
                    modelId = responseBody.asJsonObject.get("garment_id").asString

                    return modelId
                } else {
                    println(TAG + "Response body is null.")
                }
            } else {
                println(TAG + "API request failed with status code: ${response.code()}")
            }
        } catch (e: Exception) {
            println(TAG + "Error fetching data: ${e.message}")
        }

        return modelId
    }

    suspend fun modifyGarment(garment: GarmentToModify): String {
        var garmentId = ""

        try {
            // Make request to modify_garment endpoint.
            val response = api.modifyGarment(publicKey, oneTimeCode, timestamp, garment)

            // Handle response, return garmentId returned from revery
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    println(TAG + "Raw JSON Response: $responseBody")
                    garmentId = responseBody.asJsonObject.get("garment_id").asString

                    return garmentId
                } else {
                    println(TAG + "Response body is null.")
                }
            } else {
                println(TAG + "API request failed with status code: ${response.code()}")
            }
        } catch (e: Exception) {
            println(TAG + "Error fetching data: ${e.message}")
        }

        return garmentId
    }

}

