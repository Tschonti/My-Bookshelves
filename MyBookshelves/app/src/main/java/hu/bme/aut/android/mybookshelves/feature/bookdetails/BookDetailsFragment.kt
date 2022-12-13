package hu.bme.aut.android.mybookshelves.feature.bookdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import hu.bme.aut.android.mybookshelves.R
import hu.bme.aut.android.mybookshelves.databinding.FragmentBookDetailsBinding
import hu.bme.aut.android.mybookshelves.model.db.Book
import hu.bme.aut.android.mybookshelves.model.db.BookInShelf
import hu.bme.aut.android.mybookshelves.model.db.ShelfWithBooks
import hu.bme.aut.android.mybookshelves.sqlite.AppDatabase
import kotlin.concurrent.thread

class BookDetailsFragment : Fragment(), AddToShelfDialogFragment.AddToShelfDialogListener,
    AddNoteDialog.AddNoteDialogListener {
    private lateinit var binding: FragmentBookDetailsBinding
    private var book: Book? = null

    companion object {
        const val PARAM_BOOK = "PARAM_BOOK"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        arguments?.let {
            book = it.getSerializable(PARAM_BOOK) as Book
            thread {
                val newBook = AppDatabase.getInstance(requireContext()).bookDao()
                    .findByGoogleId(book?.googleId ?: "")
                if (newBook != null) {
                    book = newBook.book
                }
            }
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
            AddToShelfDialogFragment.newInstance(book!!)
                .show(childFragmentManager, AddToShelfDialogFragment::class.java.simpleName)
        }
        if (book?.previewLink != null) {
            binding.moreInfoBtn.setOnClickListener {
                val uri = Uri.parse(book?.previewLink)
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        if (book?.bookId == 0L) {
            binding.addNoteBtn.isVisible = false
        }
        binding.addNoteBtn.setOnClickListener {
            AddNoteDialog.newInstance(book?.note).show(
                childFragmentManager,
                AddNoteDialog::class.java.simpleName
            )
        }
        if (book?.note?.isNotBlank() == true) {
            binding.noteText.text = book!!.note
        }
    }

    override fun onBookToShelvesAdded(shelves: Set<ShelfWithBooks>) {
        thread {
            if (book != null) {
                var id = book?.bookId ?: 0
                if (id == 0L) {
                    id = AppDatabase.getInstance(requireContext()).bookDao().insert(book!!)
                    book!!.bookId = id
                }
                AppDatabase.getInstance(requireContext()).bookInShelfDao().deleteByBookId(id)
                AppDatabase.getInstance(requireContext()).bookInShelfDao()
                    .insertAll(shelves.map { BookInShelf(id, it.shelf.shelfId) })
                activity?.runOnUiThread {
                    binding.addNoteBtn.isVisible = true
                }
            }
        }
    }

    override fun onNoteAdded(note: String) {
        if (book?.bookId != 0L) {
            thread {
                AppDatabase.getInstance(requireContext()).bookDao().addNote(book!!.bookId, note)
                activity?.runOnUiThread {
                    binding.noteText.text = note
                    book?.note = note
                }
            }
        }
    }
}