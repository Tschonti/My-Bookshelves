package hu.bme.aut.android.mybookshelves.model.db

import java.io.Serializable

data class Bookshelf (
    val books: List<Book>,
    val name: String) : Serializable