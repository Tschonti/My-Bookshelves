package hu.bme.aut.android.mybookshelves.feature.bookdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.android.mybookshelves.R
import hu.bme.aut.android.mybookshelves.databinding.FragmentBookDetailsBinding
import hu.bme.aut.android.mybookshelves.databinding.FragmentBookListBinding
import hu.bme.aut.android.mybookshelves.model.db.Book

class BookDetailsFragment : Fragment() {
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
        binding.authorText.text = book?.authors?.joinToString(", ")
    }


}