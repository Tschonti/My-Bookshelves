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
import hu.bme.aut.android.mybookshelves.model.db.Bookshelf
import hu.bme.aut.android.mybookshelves.model.db.ShelfWithBooks
import hu.bme.aut.android.mybookshelves.sqlite.AppDatabase
import kotlin.concurrent.thread

class AddToShelfDialogFragment : AppCompatDialogFragment(),
    ShelvesToAddAdapter.OnShelfCheckedListener {

    private lateinit var binding: DialogAddToShelfBinding
    private lateinit var listener: AddToShelfDialogListener
    private lateinit var adapter: ShelvesToAddAdapter

    private val selectedShelves: MutableSet<Bookshelf> = mutableSetOf()

    interface AddToShelfDialogListener {
        fun onBookToShelvesAdded(shelves: Set<Bookshelf>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogAddToShelfBinding.inflate(LayoutInflater.from(context))
        adapter = ShelvesToAddAdapter(this)
        binding.shelfListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.shelfListRecyclerView.adapter = adapter

        listener = parentFragment as? AddToShelfDialogListener
            ?: throw RuntimeException("Fragment must implement the AddShelfDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        thread {
            val shelves = AppDatabase.getInstance(requireContext()).shelfDao().getAllWithoutBooks()
            activity?.runOnUiThread {
                Log.d("DIALOG", "ADDInG to adapter ${shelves.size}")
                adapter.replaceShelves(shelves)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onShelfSelected(shelf: Bookshelf) {
        selectedShelves.add(shelf)
    }

    override fun onShelfDeSelected(shelf: Bookshelf) {
        selectedShelves.remove(shelf)
    }
}