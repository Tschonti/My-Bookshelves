package hu.bme.aut.android.mybookshelves.feature.bookshelves

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.mybookshelves.R
import hu.bme.aut.android.mybookshelves.databinding.FragmentBookshelvesBinding
import hu.bme.aut.android.mybookshelves.feature.booklist.BookListFragment
import hu.bme.aut.android.mybookshelves.model.db.Bookshelf
import hu.bme.aut.android.mybookshelves.model.db.ShelfWithBooks
import hu.bme.aut.android.mybookshelves.sqlite.AppDatabase
import kotlin.concurrent.thread

class BookshelvesFragment : Fragment(), AddShelfDialogFragment.AddShelfDialogListener,
    ShelfAdapter.OnShelfSelectedListener {
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
            AddShelfDialogFragment().show(
                childFragmentManager,
                AddShelfDialogFragment::class.java.simpleName
            )
        }
        thread {
            val shelves = AppDatabase.getInstance(requireContext()).shelfDao().getAll()
            activity?.runOnUiThread {
                adapter.replaceShelves(shelves)
                if (shelves.isNotEmpty()) {
                    binding.noShelves.isVisible = false
                }
            }
        }
    }

    override fun onShelfAdded(shelfName: String) {
        thread {
            val db = AppDatabase.getInstance(requireContext())
            val newShelfId = db.shelfDao().insert(Bookshelf(name = shelfName))
            val newShelf = db.shelfDao().findById(newShelfId)
            activity?.runOnUiThread {
                adapter.addShelf(newShelf)
                binding.noShelves.isVisible = false
            }
        }
    }

    override fun onShelfSelected(shelf: ShelfWithBooks) {
        val bundle = Bundle()
        bundle.putSerializable(BookListFragment.PARAM_BOOKSHELF, shelf)
        findNavController().navigate(R.id.action_bookshelvesFragment_to_bookListFragment, bundle)
    }

    override fun onShelfDeleted(shelf: ShelfWithBooks) {
        thread {
            AppDatabase.getInstance(requireContext()).shelfDao().delete(shelf.shelf)
            activity?.runOnUiThread {
                adapter.removeShelf(shelf)
                if (adapter.itemCount == 0) {
                    binding.noShelves.isVisible = true
                }
            }
        }
    }

}