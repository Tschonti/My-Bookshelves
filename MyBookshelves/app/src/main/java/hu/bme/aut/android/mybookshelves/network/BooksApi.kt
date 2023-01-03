package hu.bme.aut.android.mybookshelves.network

import hu.bme.aut.android.mybookshelves.BuildConfig
import hu.bme.aut.android.mybookshelves.model.api.BooksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {
    @GET("/books/v1/volumes")
    fun getBooks(
        @Query("q") query: String?,
        @Query("maxResults") maxResults: Int? = 40,
        @Query("projection") projection: String = "lite",
        @Query("key") apiKey: String = BuildConfig.GOOGLE_API_KEY
    ): Call<BooksResponse?>?
}