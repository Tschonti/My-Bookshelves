package hu.bme.aut.android.mybookshelves.feature.bookdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import hu.bme.aut.android.mybookshelves.databinding.ActivityDetailsBinding
import hu.bme.aut.android.mybookshelves.model.api.Resource
import hu.bme.aut.android.mybookshelves.model.db.Book
import hu.bme.aut.android.mybookshelves.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var book: Book? = null
    companion object {
        const val EXTRA_BOOK = "extra.book"
        private const val TAG = "DETAILSACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        book = intent.extras?.getSerializable(EXTRA_BOOK) as Book
        binding.titleText.text = book?.title
        binding.authorText.text = book?.authors
    }
}