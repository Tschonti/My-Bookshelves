package hu.bme.aut.android.mybookshelves.feature.booklist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.mybookshelves.databinding.ItemBookBinding
import hu.bme.aut.android.mybookshelves.model.db.Book

class BookAdapter(private val listener: OnBookSelectedListener) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    private var books: MutableList<Book> = mutableListOf()

    interface OnBookSelectedListener {
        fun onBookSelected(book: Book)
        fun onBookDeleted(book: Book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder =
        BookViewHolder(
            ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = books[position]
        holder.binding.BookItemTitleTextView.text = item.title
        holder.binding.BookItemTitleTextView.setOnClickListener {
            listener.onBookSelected(item)
        }
        if (item.bookId == 0L) {
            holder.binding.deleteBtn.isVisible = false
        } else {
            holder.binding.deleteBtn.setOnClickListener {
                listener.onBookDeleted(item)
            }
        }
    }

    override fun getItemCount(): Int = books.size

    @SuppressLint("NotifyDataSetChanged")
    fun addBooks(newBooks: List<Book>) {
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }

    fun removeBook(book: Book) {
        val idx = books.indexOf(book)
        books.removeAt(idx)
        notifyItemRemoved(idx)
    }

    inner class BookViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)
}