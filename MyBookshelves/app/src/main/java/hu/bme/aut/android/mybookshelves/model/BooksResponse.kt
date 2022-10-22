package hu.bme.aut.android.mybookshelves.model

data class BooksResponse (
    val kind: String,
    val items: List<Resource>,
    val totalItems: Number
)