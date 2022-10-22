package hu.bme.aut.android.mybookshelves.network

import hu.bme.aut.android.mybookshelves.model.BooksResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private val retrofit: Retrofit
    private val booksApi: BooksApi

    private const val SERVICE_URL = "https://www.googleapis.com/"
    private const val APP_ID = "AIzaSyBQyOj9kLKWnaCetZSf8x4synAQBRElyWg"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        booksApi = retrofit.create(BooksApi::class.java)
    }

    fun getBooks(q: String?): Call<BooksResponse?>? {
        return booksApi.getBooks(q, apiKey = APP_ID)
    }
}