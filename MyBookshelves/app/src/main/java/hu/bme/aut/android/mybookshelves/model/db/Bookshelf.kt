package hu.bme.aut.android.mybookshelves.model.db

import java.io.Serializable

data class Bookshelf (
    val id: Number,
    val books: List<Book>,
    val name: String) : Serializable