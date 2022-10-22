package hu.bme.aut.android.mybookshelves.model

data class AccessInfo (

  val country          : String?,
  val epub             : Epub?  ,
  val pdf              : Pdf?   ,
  val accessViewStatus : String?,

)