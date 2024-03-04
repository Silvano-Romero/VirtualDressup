package com.myapp.revery
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// This contains all required data classes needed to convert any json response from revery to
// their respective data class types.
@Serializable
data class FilteredGarmentsResponse(
    val garments: List<String>,
    @SerialName("total_page")
    val totalPage: Int,
    val success: Boolean
){
    constructor() : this(emptyList(), 0, false)
}
@Serializable
data class Garment(
    val gender: String,
    val id: String,
    @SerialName("image_urls")
    val imageUrls: ImageUrls,
    val tryon: TryOn
){
    constructor() : this("", "", ImageUrls(), TryOn())
}
@Serializable
data class ImageUrls(
    @SerialName("product_image")
    val productImage: String
){
    constructor() : this("")
}
@Serializable
data class TryOn(
    val category: String,
    @SerialName("bottoms_sub_category")
    val bottomsSubCategory: String?, // Optional field
    @SerialName("open_outerwear")
    val openOuterwear: Boolean
){
    constructor() : this("", "",false)
}
