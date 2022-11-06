package hu.bme.aut.android.mybookshelves.network

import android.util.Log
import hu.bme.aut.android.mybookshelves.model.BooksResponse
import hu.bme.aut.android.mybookshelves.model.Resource
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

    fun getBooks(q: String?, i: Int = 0): Call<BooksResponse?>? {
        return booksApi.getBooks(q, i, apiKey = APP_ID)
    }

    fun getBook(volumeId: String): Call<Resource?>? {
        return booksApi.getBook(volumeId, apiKey = APP_ID)
    }
}