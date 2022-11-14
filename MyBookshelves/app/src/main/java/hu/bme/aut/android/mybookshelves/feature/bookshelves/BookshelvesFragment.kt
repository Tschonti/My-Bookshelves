package hu.bme.aut.android.mybookshelves.feature.bookshelves

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import hu.bme.aut.android.mybookshelves.databinding.FragmentBookshelvesBinding
import hu.bme.aut.android.mybookshelves.model.db.Bookshelf
import hu.bme.aut.android.mybookshelves.model.db.ShelfWithBooks
import hu.bme.aut.android.mybookshelves.sqlite.AppDatabase
import kotlin.concurrent.thread

class BookshelvesFragment : Fragment(), AddShelfDialogFragment.AddShelfDialogListener, ShelfAdapter.OnShelfSelectedListener {
    private lateinit var binding: FragmentBookshelvesBinding
    private lateinit var adapter: ShelfAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookshelvesBinding.inflate(inflater, container, false)
        adapter = ShelfAdapter(this)
        binding.shelfListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.shelfListRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            AddShelfDialogFragment().show(childFragmentManager, AddShelfDialogFragment::class.java.simpleName)
        }
        thread {
            val shelves = AppDatabase.getInstance(requireContext()).shelfDao().getAll()
            activity?.runOnUiThread {
                adapter.replaceShelves(shelves)
            }
        }
    }

    override fun onShelfAdded(shelfName: String) {
        Thread {
            val db = AppDatabase.getInstance(requireContext())
            val newShelfId = db.shelfDao().insert(Bookshelf(name = shelfName))
            val newShelf = db.shelfDao().findById(newShelfId)
            activity?.runOnUiThread {
                adapter.addShelf(newShelf)
            }
        }.start()
    }

    override fun onShelfSelected(shelf: ShelfWithBooks) {
        //TODO("Not yet implemented")
    }

}