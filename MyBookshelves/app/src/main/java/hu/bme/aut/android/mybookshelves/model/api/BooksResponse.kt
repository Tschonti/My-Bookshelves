package hu.bme.aut.android.mybookshelves.model.api

data class BooksResponse (
    val kind: String,
    val items: List<Resource>,
    val totalItems: Number
)