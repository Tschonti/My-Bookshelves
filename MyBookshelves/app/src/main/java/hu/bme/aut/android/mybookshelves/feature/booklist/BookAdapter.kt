package hu.bme.aut.android.mybookshelves.feature.booklist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.mybookshelves.databinding.ItemBookBinding
import hu.bme.aut.android.mybookshelves.model.Resource

class BookAdapter(private val listener: OnBookSelectedListener) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    private var books: MutableList<Resource> = mutableListOf()

    interface OnBookSelectedListener {
        fun onBookSelected(book: Resource?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder = BookViewHolder(
        ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = books[position]
        holder.binding.BookItemTitleTextView.text = item.volumeInfo?.title
        holder.binding.BookItemTitleTextView.setOnClickListener {
            listener.onBookSelected(item)
        }
    }

    override fun getItemCount(): Int = books.size

    @SuppressLint("NotifyDataSetChanged")
    fun replaceBooks(newBooks: List<Resource>) {
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }

    inner class BookViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)
}