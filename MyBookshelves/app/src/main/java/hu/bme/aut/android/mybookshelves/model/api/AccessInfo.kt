package hu.bme.aut.android.mybookshelves.model.api

data class AccessInfo (

    val country          : String?,
    val epub             : Epub?,
    val pdf              : Pdf?,
    val accessViewStatus : String?,

    )