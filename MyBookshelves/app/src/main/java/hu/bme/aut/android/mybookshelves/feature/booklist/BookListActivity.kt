package hu.bme.aut.android.mybookshelves.feature.booklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.mybookshelves.network.BooksDataHolder
import hu.bme.aut.android.mybookshelves.databinding.ActivityBooklistBinding
import hu.bme.aut.android.mybookshelves.feature.bookdetails.DetailsActivity
import hu.bme.aut.android.mybookshelves.model.BooksResponse
import hu.bme.aut.android.mybookshelves.model.Resource
import hu.bme.aut.android.mybookshelves.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookListActivity : AppCompatActivity(), BooksDataHolder, BookAdapter.OnBookSelectedListener {
    companion object {
        private const val TAG = "BooklistActivity"
    }
    private var booksData: BooksResponse? = null
    private lateinit var binding: ActivityBooklistBinding
    private lateinit var adapter: BookAdapter
    private var startIndex = 0
    private val resultsPerPage = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooklistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        binding.searchButton.setOnClickListener {
            startIndex = 0
            loadBooksData(binding.etSearch.text.toString())
        }
        binding.etSearch.setOnEditorActionListener {_, _, _ ->
            startIndex = 0
            loadBooksData(binding.etSearch.text.toString())
            return@setOnEditorActionListener true
         }
        binding.moreResultsButton.isEnabled = false
        binding.moreResultsButton.setOnClickListener {
            loadBooksData(binding.etSearch.text.toString())
        }
    }

    private fun initRecyclerView() {
        adapter = BookAdapter(this)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.adapter = adapter
    }

    override fun getBooksData(): BooksResponse? {
        return booksData
    }

    private fun loadBooksData(query: String) {
        NetworkManager.getBooks(query, startIndex)?.enqueue(object : Callback<BooksResponse?> {
            override fun onResponse(
                call: Call<BooksResponse?>,
                response: Response<BooksResponse?>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    binding.moreResultsButton.isEnabled = true
                    displayBooksData(response.body(), startIndex == 0)
                    startIndex += resultsPerPage
                } else {
                    Toast.makeText(this@BookListActivity, "Error: " + response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<BooksResponse?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(this@BookListActivity,
                    "Network request error occured, check LOG", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun displayBooksData(receivedBooksData: BooksResponse?, replace: Boolean) {
        Log.d("response", receivedBooksData.toString())
        booksData = receivedBooksData
        if (booksData != null) {
            adapter.addBooks(booksData!!.items, replace)
        }
    }

    override fun onBookSelected(book: Resource?) {
        val showDetailsIntent = Intent()
        showDetailsIntent.setClass(this, DetailsActivity::class.java)
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_BOOK, book?.id)
        startActivity(showDetailsIntent)
    }
}