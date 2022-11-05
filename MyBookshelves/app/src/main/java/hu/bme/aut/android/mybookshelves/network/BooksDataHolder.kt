package hu.bme.aut.android.mybookshelves.network

import hu.bme.aut.android.mybookshelves.model.BooksResponse

interface BooksDataHolder {
    fun getBooksData(): BooksResponse?
}