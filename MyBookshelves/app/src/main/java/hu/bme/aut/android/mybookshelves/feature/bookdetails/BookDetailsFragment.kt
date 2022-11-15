package hu.bme.aut.android.mybookshelves.feature.bookdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import hu.bme.aut.android.mybookshelves.R
import hu.bme.aut.android.mybookshelves.databinding.FragmentBookDetailsBinding
import hu.bme.aut.android.mybookshelves.databinding.FragmentBookListBinding
import hu.bme.aut.android.mybookshelves.feature.bookshelves.AddShelfDialogFragment
import hu.bme.aut.android.mybookshelves.model.db.Book
import hu.bme.aut.android.mybookshelves.model.db.BookInShelf
import hu.bme.aut.android.mybookshelves.model.db.Bookshelf
import hu.bme.aut.android.mybookshelves.model.db.ShelfWithBooks
import hu.bme.aut.android.mybookshelves.sqlite.AppDatabase
import kotlin.concurrent.thread

class BookDetailsFragment : Fragment(), AddToShelfDialogFragment.AddToShelfDialogListener {
    private lateinit var binding: FragmentBookDetailsBinding
    private var book: Book? = null

    companion object {
        const val PARAM_BOOK = "PARAM_BOOK"

        @JvmStatic
        fun newInstance(book: Book) =
            BookDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PARAM_BOOK, book)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            book = it.getSerializable(PARAM_BOOK) as Book
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.titleText.text = book?.title
        binding.authorText.text = book?.authors
        binding.descriptionText.text = book?.description
        binding.publishedText.text = context?.getString(R.string.published, book?.publishedDate)
        if (book?.thumbnail != null) {
            Glide.with(this)
                .load(book?.thumbnail?.replace("http://", "https://"))
                .into(binding.thumbnail)
        }
        binding.addToShelfBtn.setOnClickListener {
            AddToShelfDialogFragment.newInstance(book!!).show(childFragmentManager, AddToShelfDialogFragment::class.java.simpleName)
        }


    }

    override fun onBookToShelvesAdded(shelves: Set<ShelfWithBooks>) {
        thread {
            if (book != null) {
                val id = AppDatabase.getInstance(requireContext()).bookDao().insert(book!!)
                AppDatabase.getInstance(requireContext()).bookInShelfDao().insertAll(shelves.map { BookInShelf(id, it.shelf.shelfId) })

            }
        }
    }


}