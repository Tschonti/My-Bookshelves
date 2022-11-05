package hu.bme.aut.android.mybookshelves.feature.bookdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import hu.bme.aut.android.mybookshelves.R
import hu.bme.aut.android.mybookshelves.databinding.ActivityBooklistBinding
import hu.bme.aut.android.mybookshelves.databinding.ActivityDetailsBinding
import hu.bme.aut.android.mybookshelves.feature.booklist.BookListActivity
import hu.bme.aut.android.mybookshelves.model.BooksResponse
import hu.bme.aut.android.mybookshelves.model.Resource
import hu.bme.aut.android.mybookshelves.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var bookId: String? = null
    private var bookData: Resource? = null
    companion object {
        const val EXTRA_BOOK = "extra.book"
        private const val TAG = "DETAILSACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bookId = intent.getStringExtra(EXTRA_BOOK)
        loadBookData()
    }

    private fun loadBookData() {
        if (bookId != null) {
            NetworkManager.getBook(bookId!!)?.enqueue(object : Callback<Resource?> {
                override fun onResponse(
                    call: Call<Resource?>,
                    response: Response<Resource?>
                ) {
                    Log.d(TAG, "onResponse: " + response.code())
                    if (response.isSuccessful) {
                        displayBookData(response.body())
                    } else {
                        Toast.makeText(this@DetailsActivity, "Error: " + response.message(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(
                    call: Call<Resource?>,
                    throwable: Throwable
                ) {
                    throwable.printStackTrace()
                    Toast.makeText(this@DetailsActivity,
                        "Network request error occured, check LOG", Toast.LENGTH_LONG).show()
                }
            })
        }

    }

    private fun displayBookData(receivedBookData: Resource?) {
        bookData = receivedBookData
        binding.titleText.text = bookData?.volumeInfo?.title
        binding.authorText.text = bookData?.volumeInfo?.authors?.joinToString(", ")
    }
}