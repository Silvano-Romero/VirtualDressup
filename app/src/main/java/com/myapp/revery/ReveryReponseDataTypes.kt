package com.myapp.revery
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// This contains all required data classes needed to convert any json response from revery to
// their respective data class types.
@Serializable
data class FilteredGarmentsResponse(
    val garments: List<Garment>,
    @SerialName("total_page")
    val totalPage: Int,
    val success: Boolean
){ constructor() : this(emptyList(), 0, false) }
@Serializable
data class Garment(
    val gender: String,
    val id: String,
    @SerialName("image_urls")
    val imageUrls: ImageUrls,
    val tryon: TryOn
){ constructor() : this("", "", ImageUrls(), TryOn()) }
@Serializable
data class ImageUrls(
    @SerialName("product_image")
    val productImage: String
){ constructor() : this("") }
@Serializable
data class TryOn(
    val category: String,
    @SerialName("bottoms_sub_category")
    val bottomsSubCategory: String?, // Optional field
    @SerialName("open_outerwear")
    val openOuterwear: Boolean
){ constructor() : this("", "",false) }
data class GarmentToUpload(
    val category: String,
    val bottoms_sub_category: String? = null,
    val gender: String,
    val garment_img_url: String,
    val brand: String? = null,
    val url: String? = null
)
data class GarmentToDelete(
    val garment_id: String
)
@Serializable
data class Models(
    val model_ids: List<String>
){ constructor() : this(emptyList()) }

// Represents a request to delete a model
data class ModelToDelete(
    val model_id: String
)

// Represents a request to try on a garment
/*@Serializable
data class TryOnRequest(
    val garment_id: String,
    val model_id: String,
    val fitting_room_id: String
)

 */

// Data class representing the request body for the Try-on request
data class TryOnRequest(
    val garments: Map<String, String>,
    val modelId: String,
    val shoesId: String?,
    val background: String,
    val tuckIn: Boolean
)

// Data class representing the response from the Try-on request
data class TryOnResponse(
    val modelMetadata: ModelMetadata,
    val success: Boolean
)
{ constructor() : this(ModelMetadata(), false) }

// Data class representing the model metadata in the Try-on response
data class ModelMetadata(
    val gender: String,
    val modelFile: String,
    val modelId: String,
    val shoesId: String?,
    val version: String
)
{ constructor() : this("", "", "", null, "") }

// Erwin Pan worked on bottom two data classes
data class GarmentToModify(
    val garment_id: String,
    val category: String? = null, // optional field
    val sub_category: String? = null, // optional field
    val gender: String? = null, // optional field
    val brand: String? = null, // optional field
    val url: String? = null // optional field
)

data class ModelToUpload(
    val gender: String,
    val model_img_url: String,
    val standardized: Boolean
)
