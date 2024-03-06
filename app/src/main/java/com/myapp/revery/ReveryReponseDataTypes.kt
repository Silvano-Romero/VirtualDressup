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
