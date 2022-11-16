package hu.bme.aut.android.mybookshelves.feature.booklist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.mybookshelves.R
import hu.bme.aut.android.mybookshelves.databinding.FragmentBookListBinding
import hu.bme.aut.android.mybookshelves.feature.bookdetails.BookDetailsFragment
import hu.bme.aut.android.mybookshelves.model.api.BooksResponse
import hu.bme.aut.android.mybookshelves.model.db.Book
import hu.bme.aut.android.mybookshelves.model.db.BookInShelf
import hu.bme.aut.android.mybookshelves.model.db.ShelfWithBooks
import hu.bme.aut.android.mybookshelves.network.NetworkManager
import hu.bme.aut.android.mybookshelves.sqlite.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class BookListFragment : Fragment(), BookAdapter.OnBookSelectedListener {
    private lateinit var binding: FragmentBookListBinding
    private var shelf: ShelfWithBooks? = null
    private lateinit var adapter: BookAdapter

    companion object {
        const val PARAM_BOOKSHELF = "BOOKSHELF"
        private const val TAG = "BOOKLIST_FRAGMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            shelf = it.getSerializable(PARAM_BOOKSHELF) as ShelfWithBooks
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookListBinding.inflate(inflater, container, false)
        adapter = BookAdapter(this)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.mainRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (shelf != null) {
            adapter.addBooks(shelf!!.books)
        }
        binding.searchButton.setOnClickListener {
            if (shelf == null) {
                loadBooksDataFromApi(binding.etSearch.text.toString())
            } else {
                searchInShelf(binding.etSearch.text.toString())
            }
        }
        binding.etSearch.setOnEditorActionListener { _, _, _ ->
            if (shelf == null) {
                loadBooksDataFromApi(binding.etSearch.text.toString())
            } else {
                searchInShelf(binding.etSearch.text.toString())
            }
            return@setOnEditorActionListener true
        }
        binding.resultCount.text =
            activity?.getString(R.string.book_s_found, if (shelf == null) 0 else shelf!!.books.size)

        if (shelf != null) {
            binding.title.text = activity?.getString(R.string.formatted_title, shelf!!.shelf.name)
        }
    }

    override fun onResume() {
        super.onResume()
        if (binding.etSearch.text.isNotEmpty()) {
            if (shelf == null) {
                loadBooksDataFromApi(binding.etSearch.text.toString())
            } else {
                searchInShelf(binding.etSearch.text.toString())
            }
        }
    }

    private fun searchInShelf(term: String) {
        val results = shelf?.books?.filter { it.title?.contains(term, ignoreCase = true) ?: false }
        adapter.addBooks(results ?: listOf())
        binding.resultCount.text = activity?.getString(R.string.book_s_found, results?.size ?: 0)
    }

    private fun loadBooksDataFromApi(query: String) {
        NetworkManager.getBooks(query)?.enqueue(object : Callback<BooksResponse?> {
            override fun onResponse(
                call: Call<BooksResponse?>,
                response: Response<BooksResponse?>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    binding.moreResultsButton.isEnabled = true
                    displayBooksData(response.body())
                } else {
                    Toast.makeText(context, "Error: " + response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(
                call: Call<BooksResponse?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(
                    context,
                    "Network request error occurred, check LOG", Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun displayBooksData(receivedBooksData: BooksResponse?) {
        Log.d("response", receivedBooksData.toString())
        val booksData = receivedBooksData?.items?.map { Book.bookFromResource(it) }
        if (booksData != null) {
            adapter.addBooks(booksData)
        }
        binding.resultCount.text = activity?.getString(R.string.book_s_found, booksData?.size ?: 0)
    }

    override fun onBookSelected(book: Book) {
        val bundle = Bundle()
        bundle.putSerializable(BookDetailsFragment.PARAM_BOOK, book)
        findNavController().navigate(R.id.action_bookListFragment_to_bookDetailsFragment, bundle)
    }

    override fun onBookDeleted(book: Book) {
        if (shelf != null) {
            thread {
                AppDatabase.getInstance(requireContext()).bookInShelfDao()
                    .delete(BookInShelf(book.bookId, shelf!!.shelf.shelfId))
                activity?.runOnUiThread {
                    adapter.removeBook(book)
                    shelf?.books?.remove(book)
                    binding.resultCount.text =
                        activity?.getString(R.string.book_s_found, adapter.itemCount)
                }
            }
        }
    }
}