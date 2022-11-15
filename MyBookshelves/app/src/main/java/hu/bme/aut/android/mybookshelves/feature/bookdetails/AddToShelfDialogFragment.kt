package hu.bme.aut.android.mybookshelves.feature.bookdetails

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.mybookshelves.R
import hu.bme.aut.android.mybookshelves.databinding.DialogAddToShelfBinding
import hu.bme.aut.android.mybookshelves.databinding.DialogNewShelfBinding
import hu.bme.aut.android.mybookshelves.feature.booklist.BookAdapter
import hu.bme.aut.android.mybookshelves.model.db.Book
import hu.bme.aut.android.mybookshelves.model.db.Bookshelf
import hu.bme.aut.android.mybookshelves.model.db.ShelfWithBooks
import hu.bme.aut.android.mybookshelves.sqlite.AppDatabase
import kotlin.concurrent.thread

class AddToShelfDialogFragment : AppCompatDialogFragment(),
    ShelvesToAddAdapter.OnShelfCheckedListener {

    private lateinit var binding: DialogAddToShelfBinding
    private lateinit var listener: AddToShelfDialogListener
    private lateinit var adapter: ShelvesToAddAdapter

    private val selectedShelves: MutableSet<ShelfWithBooks> = mutableSetOf()
    private lateinit var book: Book

    companion object {
        fun newInstance(book: Book): AddToShelfDialogFragment {
            val inst = AddToShelfDialogFragment()
            val args = Bundle()
            args.putSerializable("BOOK", book)
            inst.arguments = args
            return  inst
        }
    }

    interface AddToShelfDialogListener {
        fun onBookToShelvesAdded(shelves: Set<ShelfWithBooks>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        book = arguments?.getSerializable("BOOK") as Book
        binding = DialogAddToShelfBinding.inflate(LayoutInflater.from(context))
        adapter = ShelvesToAddAdapter(this)
        binding.shelfListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.shelfListRecyclerView.adapter = adapter

        listener = parentFragment as? AddToShelfDialogListener
            ?: throw RuntimeException("Fragment must implement the AddShelfDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        thread {
            val shelves = AppDatabase.getInstance(requireContext()).shelfDao().getAll()
            activity?.runOnUiThread {
                adapter.replaceShelves(shelves.map { Pair(it, it.books.any { bookInDb -> bookInDb.googleId == book.googleId}) })
            }
        }
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.add_to_shelves))
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { _, _ ->
                listener.onBookToShelvesAdded(
                    selectedShelves
                )
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }


    override fun onShelfSelected(shelf: ShelfWithBooks) {
        selectedShelves.add(shelf)
    }

    override fun onShelfDeSelected(shelf: ShelfWithBooks) {
        selectedShelves.remove(shelf)
    }
}