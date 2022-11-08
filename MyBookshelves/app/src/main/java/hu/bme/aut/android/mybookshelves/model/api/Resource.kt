package hu.bme.aut.android.mybookshelves.model.api

data class Resource (
    val kind       : String?,
    val id         : String?,
    val etag       : String?,
    val selfLink   : String?,
    val volumeInfo : VolumeInfo?,
    val saleInfo   : SaleInfo?,
    val accessInfo : AccessInfo?,
    val searchInfo : SearchInfo?,
)