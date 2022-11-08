package hu.bme.aut.android.mybookshelves.network

import hu.bme.aut.android.mybookshelves.model.db.Book

interface BooksDataHolder {
    fun getBooksData(): List<Book>?
}