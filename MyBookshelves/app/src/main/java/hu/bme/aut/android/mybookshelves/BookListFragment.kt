package hu.bme.aut.android.mybookshelves

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.android.mybookshelves.databinding.ActivityBooklistBinding
import hu.bme.aut.android.mybookshelves.databinding.FragmentBookListBinding
import hu.bme.aut.android.mybookshelves.feature.booklist.BookAdapter
import hu.bme.aut.android.mybookshelves.model.db.Book
import hu.bme.aut.android.mybookshelves.model.db.Bookshelf

class BookListFragment : Fragment() {
    private lateinit var binding: FragmentBookListBinding
    private var shelf: Bookshelf? = null
    private lateinit var adapter: BookAdapter

    companion object {
        const val BOOKSHELF = "BOOKSHELF"
        private const val resultsPerPage = 10

        @JvmStatic
        fun newInstance(shelf: Bookshelf) =
            BookListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BOOKSHELF, shelf)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            shelf = it.getSerializable(BOOKSHELF) as Bookshelf
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (shelf != null) {
            adapter.addBooks(shelf!!.books, true)
        }
    }
}