package hu.bme.aut.android.mybookshelves.network

import hu.bme.aut.android.mybookshelves.model.BooksResponse
import hu.bme.aut.android.mybookshelves.model.Resource
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {
    @GET("/books/v1/volumes")
    fun getBooks(
        @Query("q") query: String?,
        @Query("startIndex") i: Int?,
        @Query("projection") projection: String = "lite",
        @Query("key") apiKey: String
    ): Call<BooksResponse?>?

    @GET("/books/v1/volumes/{volumeId}")
    fun getBook(
        @Path("volumeId") volumeId: String,
        @Query("projection") projection: String = "lite",
        @Query("key") apiKey: String
    ): Call<Resource?>?
}